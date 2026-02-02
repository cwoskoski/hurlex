package com.github.chadw.intellijhurl.formatting

import com.github.chadw.intellijhurl.language.HurlLanguage
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.github.chadw.intellijhurl.psi.HurlTypes
import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.tree.TokenSet

class HurlFormattingModelBuilder : FormattingModelBuilder {

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val settings = formattingContext.codeStyleSettings
        val element = formattingContext.psiElement

        val spacingBuilder = createSpacingBuilder(settings)

        val block = HurlBlock(
            element.node,
            null,
            Indent.getNoneIndent(),
            spacingBuilder
        )

        return FormattingModelProvider.createFormattingModelForPsiFile(
            element.containingFile,
            block,
            settings
        )
    }

    companion object {
        fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
            return SpacingBuilder(settings, HurlLanguage)
                // Space after colon in headers and key-value pairs
                .after(HurlTokenTypes.COLON)
                .spaces(1)
                // No space before colon
                .before(HurlTokenTypes.COLON)
                .spaces(0)
                // Space between method and URL
                .after(TokenSet.create(
                    HurlTokenTypes.GET, HurlTokenTypes.POST, HurlTokenTypes.PUT,
                    HurlTokenTypes.DELETE, HurlTokenTypes.PATCH, HurlTokenTypes.HEAD,
                    HurlTokenTypes.OPTIONS, HurlTokenTypes.CONNECT, HurlTokenTypes.TRACE,
                    HurlTokenTypes.LINK, HurlTokenTypes.UNLINK, HurlTokenTypes.PURGE,
                    HurlTokenTypes.LOCK, HurlTokenTypes.UNLOCK, HurlTokenTypes.PROPFIND,
                    HurlTokenTypes.PROPPATCH, HurlTokenTypes.COPY, HurlTokenTypes.MOVE,
                    HurlTokenTypes.MKCOL, HurlTokenTypes.REPORT, HurlTokenTypes.SEARCH
                ))
                .spaces(1)
                // Space between HTTP version and status code
                .after(HurlTokenTypes.HTTP_VERSION)
                .spaces(1)
                // Space around operators in asserts
                .around(TokenSet.create(
                    HurlTokenTypes.EQUALS_OP, HurlTokenTypes.NOT_EQUALS_OP,
                    HurlTokenTypes.GREATER_THAN_OP, HurlTokenTypes.GREATER_THAN_OR_EQUALS_OP,
                    HurlTokenTypes.LESS_THAN_OP, HurlTokenTypes.LESS_THAN_OR_EQUALS_OP
                ))
                .spaces(1)
        }
    }
}
