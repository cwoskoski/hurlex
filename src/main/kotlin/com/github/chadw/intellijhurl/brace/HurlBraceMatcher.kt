package com.github.chadw.intellijhurl.brace

import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

class HurlBraceMatcher : PairedBraceMatcher {

    companion object {
        private val PAIRS = arrayOf(
            BracePair(HurlTokenTypes.LBRACE, HurlTokenTypes.RBRACE, true),
            BracePair(HurlTokenTypes.LBRACKET, HurlTokenTypes.RBRACKET, true),
            BracePair(HurlTokenTypes.LBRACE2, HurlTokenTypes.RBRACE2, false),
            BracePair(HurlTokenTypes.MULTILINE_STRING_OPEN, HurlTokenTypes.MULTILINE_STRING_CLOSE, true),
        )
    }

    override fun getPairs(): Array<BracePair> = PAIRS

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean = true

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int = openingBraceOffset
}
