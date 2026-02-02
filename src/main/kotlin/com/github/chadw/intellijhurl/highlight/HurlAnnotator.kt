package com.github.chadw.intellijhurl.highlight

import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

class HurlAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val elementType = element.node?.elementType ?: return

        when (elementType) {
            HurlTokenTypes.TEMPLATE_VAR -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(HurlSyntaxHighlighter.TEMPLATE_VAR)
                    .create()
            }
            HurlTokenTypes.URL_VALUE -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(HurlSyntaxHighlighter.URL)
                    .create()
            }
        }
    }
}
