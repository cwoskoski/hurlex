package com.github.chadw.intellijhurl.run

import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment

class HurlCommandLineState(
    private val configuration: HurlRunConfiguration,
    environment: ExecutionEnvironment
) : CommandLineState(environment) {

    override fun startProcess(): ProcessHandler {
        val commandLine = GeneralCommandLine("hurl")

        val filePath = configuration.hurlFilePath
        if (!filePath.isNullOrBlank()) {
            commandLine.addParameter(filePath)
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
            commandLine.addParameter(variablesFile)
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

        commandLine.addParameter("--color")

        val processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine)
        ProcessTerminatedListener.attach(processHandler)
        return processHandler
    }
}
