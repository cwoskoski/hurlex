package com.github.chadw.intellijhurl.inspection

import com.github.chadw.intellijhurl.language.HurlFile
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile

class HurlEmptySectionInspection : LocalInspectionTool() {

    override fun getGroupDisplayName(): String = HurlInspectionBundle.GROUP_NAME
    override fun getDisplayName(): String = "Empty section"
    override fun getShortName(): String = "HurlEmptySection"
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
        for (child in element.children) {
            val nodeType = child.node?.elementType?.toString() ?: ""

            // Check if this is a section container (ends with _SECTION)
            if (nodeType.endsWith("_SECTION")) {
                val sectionChildren = child.children
                // A section with only the section header token and whitespace/newlines is empty
                val meaningfulChildren = sectionChildren.filter {
                    val type = it.node?.elementType
                    type != null
                        && !HurlTokenTypes.SECTIONS.contains(type)
                        && type != HurlTokenTypes.NEWLINE
                        && type != HurlTokenTypes.WHITE_SPACE
                }
                if (meaningfulChildren.isEmpty()) {
                    // Find the section header element for the warning
                    val sectionHeader = sectionChildren.firstOrNull {
                        HurlTokenTypes.SECTIONS.contains(it.node?.elementType)
                    }
                    if (sectionHeader != null) {
                        holder.registerProblem(sectionHeader, "Empty section '${sectionHeader.text}'")
                    }
                }
            }

            checkElement(child, holder)
        }
    }
}
