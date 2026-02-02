package com.github.chadw.intellijhurl.highlight

import com.github.chadw.intellijhurl.lexer.HurlLexerAdapter
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class HurlSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        val METHOD = createTextAttributesKey("HURL_METHOD", DefaultLanguageHighlighterColors.KEYWORD)
        val URL = createTextAttributesKey("HURL_URL", DefaultLanguageHighlighterColors.STRING)
        val HTTP_VERSION = createTextAttributesKey("HURL_HTTP_VERSION", DefaultLanguageHighlighterColors.KEYWORD)
        val STATUS_CODE = createTextAttributesKey("HURL_STATUS_CODE", DefaultLanguageHighlighterColors.NUMBER)
        val SECTION = createTextAttributesKey("HURL_SECTION", DefaultLanguageHighlighterColors.METADATA)
        val QUERY_KEYWORD = createTextAttributesKey("HURL_QUERY_KEYWORD", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val PREDICATE_KEYWORD = createTextAttributesKey("HURL_PREDICATE_KEYWORD", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val FILTER_KEYWORD = createTextAttributesKey("HURL_FILTER_KEYWORD", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val OPTION_KEYWORD = createTextAttributesKey("HURL_OPTION_KEYWORD", DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        val STRING = createTextAttributesKey("HURL_STRING", DefaultLanguageHighlighterColors.STRING)
        val NUMBER = createTextAttributesKey("HURL_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val BOOLEAN = createTextAttributesKey("HURL_BOOLEAN", DefaultLanguageHighlighterColors.KEYWORD)
        val NULL = createTextAttributesKey("HURL_NULL", DefaultLanguageHighlighterColors.KEYWORD)
        val COMMENT = createTextAttributesKey("HURL_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val HEADER_KEY = createTextAttributesKey("HURL_HEADER_KEY", DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        val HEADER_VALUE = createTextAttributesKey("HURL_HEADER_VALUE", DefaultLanguageHighlighterColors.STRING)
        val TEMPLATE_VAR = createTextAttributesKey("HURL_TEMPLATE_VAR", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR)
        val TEMPLATE_BRACE = createTextAttributesKey("HURL_TEMPLATE_BRACE", DefaultLanguageHighlighterColors.BRACES)
        val OPERATOR = createTextAttributesKey("HURL_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val KEY = createTextAttributesKey("HURL_KEY", DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        val VALUE = createTextAttributesKey("HURL_VALUE", DefaultLanguageHighlighterColors.STRING)
        val MULTILINE_STRING = createTextAttributesKey("HURL_MULTILINE_STRING", DefaultLanguageHighlighterColors.STRING)
        val JSON = createTextAttributesKey("HURL_JSON", DefaultLanguageHighlighterColors.STRING)
        val BRACE = createTextAttributesKey("HURL_BRACE", DefaultLanguageHighlighterColors.BRACES)
        val BAD_CHARACTER = createTextAttributesKey("HURL_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val METHOD_KEYS = arrayOf(METHOD)
        private val URL_KEYS = arrayOf(URL)
        private val HTTP_VERSION_KEYS = arrayOf(HTTP_VERSION)
        private val STATUS_CODE_KEYS = arrayOf(STATUS_CODE)
        private val SECTION_KEYS = arrayOf(SECTION)
        private val QUERY_KEYWORD_KEYS = arrayOf(QUERY_KEYWORD)
        private val PREDICATE_KEYWORD_KEYS = arrayOf(PREDICATE_KEYWORD)
        private val FILTER_KEYWORD_KEYS = arrayOf(FILTER_KEYWORD)
        private val OPTION_KEYWORD_KEYS = arrayOf(OPTION_KEYWORD)
        private val STRING_KEYS = arrayOf(STRING)
        private val NUMBER_KEYS = arrayOf(NUMBER)
        private val BOOLEAN_KEYS = arrayOf(BOOLEAN)
        private val NULL_KEYS = arrayOf(NULL)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val HEADER_KEY_KEYS = arrayOf(HEADER_KEY)
        private val HEADER_VALUE_KEYS = arrayOf(HEADER_VALUE)
        private val TEMPLATE_VAR_KEYS = arrayOf(TEMPLATE_VAR)
        private val TEMPLATE_BRACE_KEYS = arrayOf(TEMPLATE_BRACE)
        private val OPERATOR_KEYS = arrayOf(OPERATOR)
        private val KEY_KEYS = arrayOf(KEY)
        private val VALUE_KEYS = arrayOf(VALUE)
        private val MULTILINE_STRING_KEYS = arrayOf(MULTILINE_STRING)
        private val JSON_KEYS = arrayOf(JSON)
        private val BRACE_KEYS = arrayOf(BRACE)
        private val BAD_CHARACTER_KEYS = arrayOf(BAD_CHARACTER)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }

    override fun getHighlightingLexer(): Lexer = HurlLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when {
            HurlTokenTypes.METHODS.contains(tokenType) -> METHOD_KEYS
            tokenType == HurlTokenTypes.URL_VALUE -> URL_KEYS
            tokenType == HurlTokenTypes.HTTP_VERSION -> HTTP_VERSION_KEYS
            tokenType == HurlTokenTypes.STATUS_CODE -> STATUS_CODE_KEYS
            HurlTokenTypes.SECTIONS.contains(tokenType) -> SECTION_KEYS
            HurlTokenTypes.QUERY_KEYWORDS.contains(tokenType) -> QUERY_KEYWORD_KEYS
            HurlTokenTypes.PREDICATE_KEYWORDS.contains(tokenType) -> PREDICATE_KEYWORD_KEYS
            HurlTokenTypes.FILTER_KEYWORDS.contains(tokenType) -> FILTER_KEYWORD_KEYS
            tokenType == HurlTokenTypes.HEADER_KEY -> HEADER_KEY_KEYS
            tokenType == HurlTokenTypes.HEADER_VALUE -> HEADER_VALUE_KEYS
            tokenType == HurlTokenTypes.KEY_STRING -> KEY_KEYS
            tokenType == HurlTokenTypes.VALUE_STRING -> VALUE_KEYS
            tokenType == HurlTokenTypes.QUOTED_STRING || tokenType == HurlTokenTypes.BACKTICK_STRING -> STRING_KEYS
            tokenType == HurlTokenTypes.MULTILINE_STRING_OPEN || tokenType == HurlTokenTypes.MULTILINE_STRING_CLOSE -> MULTILINE_STRING_KEYS
            tokenType == HurlTokenTypes.MULTILINE_STRING_CONTENT -> MULTILINE_STRING_KEYS
            tokenType == HurlTokenTypes.NUMBER || tokenType == HurlTokenTypes.FLOAT_NUMBER -> NUMBER_KEYS
            tokenType == HurlTokenTypes.TRUE || tokenType == HurlTokenTypes.FALSE -> BOOLEAN_KEYS
            tokenType == HurlTokenTypes.NULL -> NULL_KEYS
            tokenType == HurlTokenTypes.COMMENT -> COMMENT_KEYS
            tokenType == HurlTokenTypes.TEMPLATE_VAR -> TEMPLATE_VAR_KEYS
            tokenType == HurlTokenTypes.LBRACE2 || tokenType == HurlTokenTypes.RBRACE2 -> TEMPLATE_BRACE_KEYS
            tokenType == HurlTokenTypes.EQUALS_OP || tokenType == HurlTokenTypes.NOT_EQUALS_OP
                || tokenType == HurlTokenTypes.GREATER_THAN_OP || tokenType == HurlTokenTypes.GREATER_THAN_OR_EQUALS_OP
                || tokenType == HurlTokenTypes.LESS_THAN_OP || tokenType == HurlTokenTypes.LESS_THAN_OR_EQUALS_OP -> OPERATOR_KEYS
            tokenType == HurlTokenTypes.LBRACE || tokenType == HurlTokenTypes.RBRACE
                || tokenType == HurlTokenTypes.LBRACKET || tokenType == HurlTokenTypes.RBRACKET -> BRACE_KEYS
            tokenType == HurlTokenTypes.JSON_TEXT -> JSON_KEYS
            tokenType == HurlTokenTypes.BAD_CHARACTER -> BAD_CHARACTER_KEYS
            else -> EMPTY_KEYS
        }
    }
}
