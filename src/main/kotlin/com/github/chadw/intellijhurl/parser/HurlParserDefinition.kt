package com.github.chadw.intellijhurl.parser

import com.github.chadw.intellijhurl.language.HurlFile
import com.github.chadw.intellijhurl.language.HurlLanguage
import com.github.chadw.intellijhurl.lexer.HurlLexerAdapter
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.github.chadw.intellijhurl.psi.HurlTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class HurlParserDefinition : ParserDefinition {
    companion object {
        val FILE = IFileElementType(HurlLanguage)
    }

    override fun createLexer(project: Project?): Lexer = HurlLexerAdapter()

    override fun createParser(project: Project?): PsiParser = HurlParser()

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getCommentTokens(): TokenSet = HurlTokenTypes.COMMENTS

    override fun getWhitespaceTokens(): TokenSet = HurlTokenTypes.WHITESPACES

    override fun getStringLiteralElements(): TokenSet = HurlTokenTypes.STRINGS

    override fun createElement(node: ASTNode): PsiElement = HurlTypes.Factory.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = HurlFile(viewProvider)
}
