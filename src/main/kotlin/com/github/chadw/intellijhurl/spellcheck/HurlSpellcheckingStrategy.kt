package com.github.chadw.intellijhurl.spellcheck

import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.psi.PsiElement
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy
import com.intellij.spellchecker.tokenizer.Tokenizer

class HurlSpellcheckingStrategy : SpellcheckingStrategy() {

    override fun getTokenizer(element: PsiElement): Tokenizer<*> {
        val tokenType = element.node?.elementType

        return when (tokenType) {
            HurlTokenTypes.COMMENT -> TEXT_TOKENIZER
            HurlTokenTypes.QUOTED_STRING -> TEXT_TOKENIZER
            HurlTokenTypes.MULTILINE_STRING_CONTENT -> TEXT_TOKENIZER
            else -> EMPTY_TOKENIZER
        }
    }
}
