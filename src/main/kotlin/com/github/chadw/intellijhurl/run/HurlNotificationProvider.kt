package com.github.chadw.intellijhurl.run

import com.github.chadw.intellijhurl.language.HurlFileType
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EditorNotificationPanel
import com.intellij.ui.EditorNotificationProvider
import java.util.function.Function
import javax.swing.JComponent

class HurlNotificationProvider : EditorNotificationProvider, DumbAware {

    override fun collectNotificationData(
        project: Project,
        file: VirtualFile
    ): Function<in FileEditor, out JComponent?> {
        return Function { _ ->
            if (file.fileType != HurlFileType) return@Function null
            if (isHurlInstalled()) return@Function null

            EditorNotificationPanel(EditorNotificationPanel.Status.Warning).apply {
                text = "Hurl CLI is not found on PATH. Install it to run .hurl files."
                createActionLabel("Installation Guide") {
                    com.intellij.ide.BrowserUtil.browse("https://hurl.dev/docs/installation.html")
                }
            }
        }
    }

    companion object {
        private var cachedResult: Boolean? = null

        fun isHurlInstalled(): Boolean {
            cachedResult?.let { return it }

            val result = try {
                val process = ProcessBuilder("hurl", "--version")
                    .redirectErrorStream(true)
                    .start()
                val exitCode = process.waitFor()
                exitCode == 0
            } catch (_: Exception) {
                false
            }

            cachedResult = result
            return result
        }

        fun clearCache() {
            cachedResult = null
        }
    }
}
