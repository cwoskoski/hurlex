package com.github.chadw.intellijhurl.inspection

import com.github.chadw.intellijhurl.language.HurlFile
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile

class HurlDuplicateHeaderInspection : LocalInspectionTool() {

    override fun getGroupDisplayName(): String = HurlInspectionBundle.GROUP_NAME
    override fun getDisplayName(): String = "Duplicate header in request"
    override fun getShortName(): String = "HurlDuplicateHeader"
    override fun isEnabledByDefault(): Boolean = true

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitFile(file: PsiFile) {
                if (file !is HurlFile) return

                // Walk through entries and find duplicate headers within each request
                for (entry in file.children) {
                    val entryType = entry.node?.elementType?.toString() ?: continue
                    if (entryType != "ENTRY") continue

                    checkDuplicateHeaders(entry, holder)
                }
            }
        }
    }

    private fun checkDuplicateHeaders(entry: com.intellij.psi.PsiElement, holder: ProblemsHolder) {
        val headerKeys = mutableMapOf<String, com.intellij.psi.PsiElement>()

        fun walkChildren(element: com.intellij.psi.PsiElement) {
            for (child in element.children) {
                val nodeType = child.node?.elementType
                if (nodeType == HurlTokenTypes.HEADER_KEY) {
                    val key = child.text.lowercase()
                    val existing = headerKeys[key]
                    if (existing != null) {
                        holder.registerProblem(child, "Duplicate header '${child.text}'")
                    } else {
                        headerKeys[key] = child
                    }
                }
                // Stop at response boundary or section
                val childType = child.node?.elementType?.toString() ?: ""
                if (childType == "RESPONSE" || HurlTokenTypes.SECTIONS.contains(child.node?.elementType)) {
                    return
                }
                walkChildren(child)
            }
        }

        walkChildren(entry)
    }
}
