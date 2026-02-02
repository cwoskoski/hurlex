package com.github.chadw.intellijhurl.reference

import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.github.chadw.intellijhurl.lexer.HurlLexerAdapter

class HurlFindUsagesProvider : FindUsagesProvider {

    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(
            HurlLexerAdapter(),
            TokenSet.create(HurlTokenTypes.TEMPLATE_VAR, HurlTokenTypes.KEY_STRING),
            HurlTokenTypes.COMMENTS,
            HurlTokenTypes.STRINGS
        )
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean {
        val type = psiElement.node?.elementType
        return type == HurlTokenTypes.TEMPLATE_VAR || type == HurlTokenTypes.KEY_STRING
    }

    override fun getHelpId(psiElement: PsiElement): String? = null

    override fun getType(element: PsiElement): String {
        return when (element.node?.elementType) {
            HurlTokenTypes.TEMPLATE_VAR -> "template variable"
            HurlTokenTypes.KEY_STRING -> "capture variable"
            else -> "element"
        }
    }

    override fun getDescriptiveName(element: PsiElement): String {
        return element.text ?: ""
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return element.text ?: ""
    }
}
