package com.github.chadw.intellijhurl.inspection

import com.github.chadw.intellijhurl.language.HurlFile
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile

class HurlInvalidStatusCodeInspection : LocalInspectionTool() {

    companion object {
        private val VALID_STATUS_RANGE = 100..599
    }

    override fun getGroupDisplayName(): String = HurlInspectionBundle.GROUP_NAME
    override fun getDisplayName(): String = "Invalid HTTP status code"
    override fun getShortName(): String = "HurlInvalidStatusCode"
    override fun isEnabledByDefault(): Boolean = true

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitFile(file: PsiFile) {
                if (file !is HurlFile) return
                checkElement(file, holder)
            }
        }
    }

    private fun checkElement(element: PsiElement, holder: ProblemsHolder) {
        if (element.node?.elementType == HurlTokenTypes.STATUS_CODE) {
            val code = element.text.trim().toIntOrNull()
            if (code == null || code !in VALID_STATUS_RANGE) {
                holder.registerProblem(element, "Invalid HTTP status code '${element.text}'")
            }
        }
        for (child in element.children) {
            checkElement(child, holder)
        }
    }
}
