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

            val location = HurlExecutableUtil.findHurl()
            if (location != null) return@Function null

            EditorNotificationPanel(EditorNotificationPanel.Status.Warning).apply {
                text = "Hurl CLI not found (checked PATH, common install locations, and WSL). Install it to run .hurl files."
                createActionLabel("Installation Guide") {
                    com.intellij.ide.BrowserUtil.browse("https://hurl.dev/docs/installation.html")
                }
                createActionLabel("Retry Detection") {
                    HurlExecutableUtil.clearCache()
                    com.intellij.openapi.fileEditor.FileEditorManager.getInstance(project)
                        .selectedEditors.forEach { it.file?.let { f ->
                            com.intellij.ui.EditorNotifications.getInstance(project).updateNotifications(f)
                        }}
                }
            }
        }
    }
}
