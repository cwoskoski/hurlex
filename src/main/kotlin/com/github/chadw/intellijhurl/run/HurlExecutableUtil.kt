package com.github.chadw.intellijhurl.run

import java.io.File

/**
 * Utility for locating the hurl executable across platforms:
 * - Direct PATH lookup (Linux, macOS, Windows native)
 * - WSL lookup from Windows (wsl.exe -e hurl)
 * - Common installation directories
 * - User-configured custom path
 */
object HurlExecutableUtil {

    data class HurlLocation(
        /** The executable command or path */
        val executable: String,
        /** Additional prefix args (e.g., for WSL: ["wsl.exe", "-e"]) */
        val prefixArgs: List<String> = emptyList(),
        /** Human-readable description of where hurl was found */
        val description: String
    ) {
        /** Build the full command list for ProcessBuilder / GeneralCommandLine */
        fun buildCommand(): List<String> = prefixArgs + executable
    }

    private var cachedLocation: HurlLocation? = null
    private var cacheChecked = false

    private val isWindows = System.getProperty("os.name").lowercase().contains("win")

    /**
     * Find the hurl executable, checking in order:
     * 1. User-configured custom path (if provided)
     * 2. Native PATH
     * 3. Common install directories
     * 4. WSL (on Windows)
     *
     * Returns null if hurl cannot be found anywhere.
     */
    fun findHurl(customPath: String? = null): HurlLocation? {
        // Custom path always takes priority
        if (!customPath.isNullOrBlank()) {
            val loc = checkExecutable(customPath, "Custom path")
            if (loc != null) return loc
        }

        // Return cached result for auto-detection
        if (cacheChecked) return cachedLocation

        cachedLocation = detectHurl()
        cacheChecked = true
        return cachedLocation
    }

    /**
     * Check if hurl is available by any means.
     */
    fun isHurlAvailable(customPath: String? = null): Boolean {
        return findHurl(customPath) != null
    }

    fun clearCache() {
        cachedLocation = null
        cacheChecked = false
    }

    private fun detectHurl(): HurlLocation? {
        // 1. Check native PATH
        checkNativePath()?.let { return it }

        // 2. Check common install locations
        checkCommonPaths()?.let { return it }

        // 3. On Windows, check WSL
        if (isWindows) {
            checkWsl()?.let { return it }
        }

        return null
    }

    private fun checkNativePath(): HurlLocation? {
        return try {
            val process = ProcessBuilder("hurl", "--version")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText().trim()
            val exitCode = process.waitFor()
            if (exitCode == 0) {
                HurlLocation("hurl", description = "PATH ($output)")
            } else null
        } catch (_: Exception) {
            null
        }
    }

    private fun checkCommonPaths(): HurlLocation? {
        val candidates = if (isWindows) {
            listOf(
                "${System.getenv("LOCALAPPDATA") ?: ""}\\Microsoft\\WinGet\\Packages\\hurl",
                "${System.getenv("USERPROFILE") ?: ""}\\scoop\\shims\\hurl.exe",
                "C:\\Program Files\\hurl\\bin\\hurl.exe",
                "C:\\ProgramData\\chocolatey\\bin\\hurl.exe"
            )
        } else {
            listOf(
                "/usr/local/bin/hurl",
                "/usr/bin/hurl",
                "/opt/homebrew/bin/hurl",
                "${System.getenv("HOME") ?: ""}/.cargo/bin/hurl"
            )
        }

        for (path in candidates) {
            val file = File(path)
            if (file.exists() && file.canExecute()) {
                return checkExecutable(path, "Found at $path")
            }
        }
        return null
    }

    private fun checkWsl(): HurlLocation? {
        // First check if wsl.exe is available
        val wslAvailable = try {
            val p = ProcessBuilder("wsl.exe", "--status")
                .redirectErrorStream(true)
                .start()
            p.inputStream.bufferedReader().readText()
            p.waitFor() == 0
        } catch (_: Exception) {
            // --status may not exist on older WSL; try a simpler check
            try {
                val p = ProcessBuilder("wsl.exe", "echo", "ok")
                    .redirectErrorStream(true)
                    .start()
                val out = p.inputStream.bufferedReader().readText().trim()
                p.waitFor() == 0 && out.contains("ok")
            } catch (_: Exception) {
                false
            }
        }

        if (!wslAvailable) return null

        // Check if hurl is installed inside WSL
        return try {
            val process = ProcessBuilder("wsl.exe", "-e", "hurl", "--version")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText().trim()
            val exitCode = process.waitFor()
            if (exitCode == 0) {
                HurlLocation(
                    executable = "hurl",
                    prefixArgs = listOf("wsl.exe", "-e"),
                    description = "WSL ($output)"
                )
            } else null
        } catch (_: Exception) {
            null
        }
    }

    private fun checkExecutable(path: String, description: String): HurlLocation? {
        return try {
            val process = ProcessBuilder(path, "--version")
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText().trim()
            val exitCode = process.waitFor()
            if (exitCode == 0) {
                HurlLocation(path, description = "$description ($output)")
            } else null
        } catch (_: Exception) {
            null
        }
    }
}
