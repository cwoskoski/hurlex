package com.github.chadw.intellijhurl.navigation

import com.github.chadw.intellijhurl.language.HurlLanguage
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.lang.Language
import com.intellij.psi.PsiElement
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider

class HurlBreadcrumbsProvider : BreadcrumbsProvider {

    override fun getLanguages(): Array<Language> = arrayOf(HurlLanguage)

    override fun acceptElement(element: PsiElement): Boolean {
        val typeName = element.node?.elementType?.toString() ?: return false
        return typeName == "ENTRY" || typeName == "REQUEST" || typeName == "RESPONSE"
            || typeName.endsWith("_SECTION")
    }

    override fun getElementInfo(element: PsiElement): String {
        val typeName = element.node?.elementType?.toString() ?: return ""

        if (typeName == "ENTRY" || typeName == "REQUEST") {
            val firstLine = element.text.lineSequence().firstOrNull()?.trim() ?: return "Request"
            return firstLine.take(50)
        }

        if (typeName == "RESPONSE") {
            val firstLine = element.text.lineSequence().firstOrNull()?.trim() ?: return "Response"
            return firstLine.take(30)
        }

        if (typeName.endsWith("_SECTION")) {
            val sectionToken = element.children.firstOrNull {
                HurlTokenTypes.SECTIONS.contains(it.node?.elementType)
            }
            return sectionToken?.text ?: typeName.removeSuffix("_SECTION")
        }

        return element.text.take(30)
    }
}
