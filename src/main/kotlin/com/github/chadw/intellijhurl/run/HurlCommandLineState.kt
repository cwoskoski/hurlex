package com.github.chadw.intellijhurl.run

import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment

class HurlCommandLineState(
    private val configuration: HurlRunConfiguration,
    environment: ExecutionEnvironment
) : CommandLineState(environment) {

    override fun startProcess(): ProcessHandler {
        val customPath = configuration.hurlExecutable?.takeIf { it.isNotBlank() }
        val location = HurlExecutableUtil.findHurl(customPath)

        val commandLine: GeneralCommandLine
        if (location != null) {
            // Use detected hurl (may be via WSL or direct)
            val fullCommand = location.buildCommand()
            commandLine = GeneralCommandLine(fullCommand)
        } else {
            // Fallback to bare "hurl" and let the OS error if not found
            commandLine = GeneralCommandLine("hurl")
        }

        val filePath = configuration.hurlFilePath
        if (!filePath.isNullOrBlank()) {
            // When running through WSL, convert Windows path to WSL path
            if (location?.prefixArgs?.contains("wsl.exe") == true) {
                commandLine.addParameter(toWslPath(filePath))
            } else {
                commandLine.addParameter(filePath)
            }
        }

        // Test mode
        if (configuration.testMode) {
            commandLine.addParameter("--test")
        }

        // Verbose flags
        if (configuration.veryVerbose) {
            commandLine.addParameter("--very-verbose")
        } else if (configuration.verbose) {
            commandLine.addParameter("--verbose")
        }

        val options = configuration.hurlOptions
        if (!options.isNullOrBlank()) {
            options.split("\\s+".toRegex()).filter { it.isNotBlank() }.forEach {
                commandLine.addParameter(it)
            }
        }

        val variablesFile = configuration.variablesFile
        if (!variablesFile.isNullOrBlank()) {
            commandLine.addParameter("--variables-file")
            if (location?.prefixArgs?.contains("wsl.exe") == true) {
                commandLine.addParameter(toWslPath(variablesFile))
            } else {
                commandLine.addParameter(variablesFile)
            }
        }

        val workDir = configuration.workingDirectory
        if (!workDir.isNullOrBlank()) {
            commandLine.workDirectory = java.io.File(workDir)
        } else {
            val projectDir = environment.project.basePath
            if (projectDir != null) {
                commandLine.workDirectory = java.io.File(projectDir)
            }
        }

        // Environment variables
        val envVars = configuration.environmentVariables
        if (!envVars.isNullOrBlank()) {
            envVars.split(";").filter { it.contains("=") }.forEach { pair ->
                val key = pair.substringBefore("=").trim()
                val value = pair.substringAfter("=").trim()
                if (key.isNotBlank()) {
                    commandLine.environment[key] = value
                }
            }
        }

        commandLine.addParameter("--color")

        val processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine)
        ProcessTerminatedListener.attach(processHandler)
        return processHandler
    }

    /**
     * Convert a Windows path (e.g., C:\Users\foo\file.hurl) to a WSL path (/mnt/c/Users/foo/file.hurl).
     */
    private fun toWslPath(windowsPath: String): String {
        val normalized = windowsPath.replace("\\", "/")

        // Handle WSL UNC paths: //wsl.localhost/<Distro>/path or //wsl$//<Distro>/path
        val wslUncRegex = Regex("^//wsl(?:\\.localhost|\\$)/[^/]+(/.*)")
        val wslUncMatch = wslUncRegex.matchEntire(normalized)
        if (wslUncMatch != null) {
            return wslUncMatch.groupValues[1]
        }

        // If it already looks like a Unix path, return as-is
        if (normalized.startsWith("/")) return normalized

        // Convert drive letter: C:\foo -> /mnt/c/foo
        val driveRegex = Regex("^([A-Za-z]):/(.*)$")
        val driveMatch = driveRegex.matchEntire(normalized)
        return if (driveMatch != null) {
            val drive = driveMatch.groupValues[1].lowercase()
            val rest = driveMatch.groupValues[2]
            "/mnt/$drive/$rest"
        } else {
            normalized
        }
    }
}
