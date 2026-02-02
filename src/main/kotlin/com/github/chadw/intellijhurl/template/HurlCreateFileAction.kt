package com.github.chadw.intellijhurl.template

import com.github.chadw.intellijhurl.language.HurlIcons
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

class HurlCreateFileAction : CreateFileFromTemplateAction(
    "Hurl File",
    "Creates a new Hurl HTTP test file",
    HurlIcons.FILE
), DumbAware {

    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("New Hurl File")
            .addKind("Hurl File", HurlIcons.FILE, "Hurl File")
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return "Create Hurl File $newName"
    }
}
