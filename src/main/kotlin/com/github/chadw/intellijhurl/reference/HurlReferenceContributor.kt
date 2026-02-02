package com.github.chadw.intellijhurl.reference

import com.github.chadw.intellijhurl.language.HurlLanguage
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext

class HurlReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement().withLanguage(HurlLanguage),
            HurlTemplateVarReferenceProvider()
        )
    }
}

class HurlTemplateVarReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        if (element.node?.elementType != HurlTokenTypes.TEMPLATE_VAR) {
            return PsiReference.EMPTY_ARRAY
        }

        val varName = element.text
        return arrayOf(HurlTemplateVarReference(element, TextRange(0, varName.length)))
    }
}

class HurlTemplateVarReference(
    element: PsiElement,
    textRange: TextRange
) : PsiReferenceBase<PsiElement>(element, textRange) {

    override fun resolve(): PsiElement? {
        val varName = element.text
        val file = element.containingFile ?: return null

        // Search for capture definitions: varName: query
        var result: PsiElement? = null
        file.accept(object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (result != null) return
                val node = element.node ?: return
                if (node.elementType == HurlTokenTypes.KEY_STRING && element.text == varName) {
                    // Check if this is in a [Captures] section
                    val parent = element.parent
                    val grandParent = parent?.parent
                    if (grandParent?.node?.elementType?.toString()?.contains("CAPTURE", ignoreCase = true) == true) {
                        result = element
                        return
                    }
                }
                super.visitElement(element)
            }
        })

        return result
    }
}
