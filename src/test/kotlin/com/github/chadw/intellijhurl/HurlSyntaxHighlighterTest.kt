package com.github.chadw.intellijhurl

import com.github.chadw.intellijhurl.highlight.HurlSyntaxHighlighter
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.tree.IElementType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HurlSyntaxHighlighterTest {

    private lateinit var highlighter: HurlSyntaxHighlighter

    @BeforeEach
    fun setUp() {
        highlighter = HurlSyntaxHighlighter()
    }

    private fun highlights(tokenType: IElementType): Array<TextAttributesKey> =
        highlighter.getTokenHighlights(tokenType)

    private fun assertHighlightsTo(tokenType: IElementType, expected: TextAttributesKey, message: String = "") {
        val keys = highlights(tokenType)
        assertEquals(1, keys.size, "Expected exactly one highlight key for $tokenType. $message")
        assertEquals(expected, keys[0], "Token $tokenType should map to $expected. $message")
    }

    // --- Method tokens ---

    @Test
    fun `test all method tokens map to METHOD key`() {
        val methodTokens = listOf(
            HurlTokenTypes.GET, HurlTokenTypes.POST, HurlTokenTypes.PUT,
            HurlTokenTypes.DELETE, HurlTokenTypes.PATCH, HurlTokenTypes.HEAD,
            HurlTokenTypes.OPTIONS, HurlTokenTypes.CONNECT, HurlTokenTypes.TRACE,
            HurlTokenTypes.LINK, HurlTokenTypes.UNLINK, HurlTokenTypes.PURGE,
            HurlTokenTypes.LOCK, HurlTokenTypes.UNLOCK, HurlTokenTypes.PROPFIND,
            HurlTokenTypes.PROPPATCH, HurlTokenTypes.COPY, HurlTokenTypes.MOVE,
            HurlTokenTypes.MKCOL, HurlTokenTypes.REPORT, HurlTokenTypes.SEARCH
        )
        for (token in methodTokens) {
            assertHighlightsTo(token, HurlSyntaxHighlighter.METHOD, "Method: $token")
        }
    }

    // --- URL ---

    @Test
    fun `test URL_VALUE maps to URL key`() {
        assertHighlightsTo(HurlTokenTypes.URL_VALUE, HurlSyntaxHighlighter.URL)
    }

    // --- HTTP Version ---

    @Test
    fun `test HTTP_VERSION maps to HTTP_VERSION key`() {
        assertHighlightsTo(HurlTokenTypes.HTTP_VERSION, HurlSyntaxHighlighter.HTTP_VERSION)
    }

    // --- Status Code ---

    @Test
    fun `test STATUS_CODE maps to STATUS_CODE key`() {
        assertHighlightsTo(HurlTokenTypes.STATUS_CODE, HurlSyntaxHighlighter.STATUS_CODE)
    }

    // --- Section tokens ---

    @Test
    fun `test all section tokens map to SECTION key`() {
        val sectionTokens = listOf(
            HurlTokenTypes.SECTION_QUERY_STRING_PARAMS,
            HurlTokenTypes.SECTION_FORM_PARAMS,
            HurlTokenTypes.SECTION_MULTIPART_FORM_DATA,
            HurlTokenTypes.SECTION_COOKIES,
            HurlTokenTypes.SECTION_BASIC_AUTH,
            HurlTokenTypes.SECTION_OPTIONS,
            HurlTokenTypes.SECTION_CAPTURES,
            HurlTokenTypes.SECTION_ASSERTS
        )
        for (token in sectionTokens) {
            assertHighlightsTo(token, HurlSyntaxHighlighter.SECTION, "Section: $token")
        }
    }

    // --- Query keyword tokens ---

    @Test
    fun `test all query keyword tokens map to QUERY_KEYWORD key`() {
        val queryTokens = listOf(
            HurlTokenTypes.KW_STATUS, HurlTokenTypes.KW_URL, HurlTokenTypes.KW_HEADER,
            HurlTokenTypes.KW_COOKIE, HurlTokenTypes.KW_BODY, HurlTokenTypes.KW_XPATH,
            HurlTokenTypes.KW_JSONPATH, HurlTokenTypes.KW_REGEX, HurlTokenTypes.KW_VARIABLE,
            HurlTokenTypes.KW_DURATION, HurlTokenTypes.KW_SHA256, HurlTokenTypes.KW_MD5,
            HurlTokenTypes.KW_BYTES, HurlTokenTypes.KW_CERTIFICATE
        )
        for (token in queryTokens) {
            assertHighlightsTo(token, HurlSyntaxHighlighter.QUERY_KEYWORD, "Query keyword: $token")
        }
    }

    // --- Predicate keyword tokens ---

    @Test
    fun `test all predicate keyword tokens map to PREDICATE_KEYWORD key`() {
        val predicateTokens = listOf(
            HurlTokenTypes.KW_EQUALS, HurlTokenTypes.KW_NOT_EQUALS,
            HurlTokenTypes.KW_GREATER_THAN, HurlTokenTypes.KW_GREATER_THAN_OR_EQUALS,
            HurlTokenTypes.KW_LESS_THAN, HurlTokenTypes.KW_LESS_THAN_OR_EQUALS,
            HurlTokenTypes.KW_STARTS_WITH, HurlTokenTypes.KW_ENDS_WITH,
            HurlTokenTypes.KW_CONTAINS, HurlTokenTypes.KW_INCLUDES,
            HurlTokenTypes.KW_MATCHES, HurlTokenTypes.KW_EXISTS,
            HurlTokenTypes.KW_IS_EMPTY, HurlTokenTypes.KW_IS_NUMBER,
            HurlTokenTypes.KW_IS_STRING, HurlTokenTypes.KW_IS_BOOLEAN,
            HurlTokenTypes.KW_IS_COLLECTION, HurlTokenTypes.KW_IS_DATE,
            HurlTokenTypes.KW_IS_ISO_DATE, HurlTokenTypes.KW_IS_FLOAT,
            HurlTokenTypes.KW_IS_INTEGER, HurlTokenTypes.KW_NOT
        )
        for (token in predicateTokens) {
            assertHighlightsTo(token, HurlSyntaxHighlighter.PREDICATE_KEYWORD, "Predicate keyword: $token")
        }
    }

    // --- Filter keyword tokens ---

    @Test
    fun `test all filter keyword tokens map to FILTER_KEYWORD key`() {
        val filterTokens = listOf(
            HurlTokenTypes.KW_COUNT, HurlTokenTypes.KW_NTH,
            HurlTokenTypes.KW_REPLACE, HurlTokenTypes.KW_SPLIT,
            HurlTokenTypes.KW_TO_DATE, HurlTokenTypes.KW_TO_FLOAT,
            HurlTokenTypes.KW_TO_INT, HurlTokenTypes.KW_DECODE,
            HurlTokenTypes.KW_FORMAT, HurlTokenTypes.KW_HTML_ESCAPE,
            HurlTokenTypes.KW_HTML_UNESCAPE, HurlTokenTypes.KW_URL_ENCODE,
            HurlTokenTypes.KW_URL_DECODE
        )
        for (token in filterTokens) {
            assertHighlightsTo(token, HurlSyntaxHighlighter.FILTER_KEYWORD, "Filter keyword: $token")
        }
    }

    // --- Header tokens ---

    @Test
    fun `test HEADER_KEY maps to HEADER_KEY key`() {
        assertHighlightsTo(HurlTokenTypes.HEADER_KEY, HurlSyntaxHighlighter.HEADER_KEY)
    }

    @Test
    fun `test HEADER_VALUE maps to HEADER_VALUE key`() {
        assertHighlightsTo(HurlTokenTypes.HEADER_VALUE, HurlSyntaxHighlighter.HEADER_VALUE)
    }

    // --- String tokens ---

    @Test
    fun `test QUOTED_STRING maps to STRING key`() {
        assertHighlightsTo(HurlTokenTypes.QUOTED_STRING, HurlSyntaxHighlighter.STRING)
    }

    @Test
    fun `test BACKTICK_STRING maps to STRING key`() {
        assertHighlightsTo(HurlTokenTypes.BACKTICK_STRING, HurlSyntaxHighlighter.STRING)
    }

    // --- Number tokens ---

    @Test
    fun `test NUMBER maps to NUMBER key`() {
        assertHighlightsTo(HurlTokenTypes.NUMBER, HurlSyntaxHighlighter.NUMBER)
    }

    @Test
    fun `test FLOAT_NUMBER maps to NUMBER key`() {
        assertHighlightsTo(HurlTokenTypes.FLOAT_NUMBER, HurlSyntaxHighlighter.NUMBER)
    }

    // --- Boolean tokens ---

    @Test
    fun `test TRUE maps to BOOLEAN key`() {
        assertHighlightsTo(HurlTokenTypes.TRUE, HurlSyntaxHighlighter.BOOLEAN)
    }

    @Test
    fun `test FALSE maps to BOOLEAN key`() {
        assertHighlightsTo(HurlTokenTypes.FALSE, HurlSyntaxHighlighter.BOOLEAN)
    }

    // --- Null ---

    @Test
    fun `test NULL maps to NULL key`() {
        assertHighlightsTo(HurlTokenTypes.NULL, HurlSyntaxHighlighter.NULL)
    }

    // --- Comment ---

    @Test
    fun `test COMMENT maps to COMMENT key`() {
        assertHighlightsTo(HurlTokenTypes.COMMENT, HurlSyntaxHighlighter.COMMENT)
    }

    // --- Template tokens ---

    @Test
    fun `test TEMPLATE_VAR maps to TEMPLATE_VAR key`() {
        assertHighlightsTo(HurlTokenTypes.TEMPLATE_VAR, HurlSyntaxHighlighter.TEMPLATE_VAR)
    }

    @Test
    fun `test LBRACE2 maps to TEMPLATE_BRACE key`() {
        assertHighlightsTo(HurlTokenTypes.LBRACE2, HurlSyntaxHighlighter.TEMPLATE_BRACE)
    }

    @Test
    fun `test RBRACE2 maps to TEMPLATE_BRACE key`() {
        assertHighlightsTo(HurlTokenTypes.RBRACE2, HurlSyntaxHighlighter.TEMPLATE_BRACE)
    }

    // --- Operator tokens ---

    @Test
    fun `test EQUALS_OP maps to OPERATOR key`() {
        assertHighlightsTo(HurlTokenTypes.EQUALS_OP, HurlSyntaxHighlighter.OPERATOR)
    }

    @Test
    fun `test NOT_EQUALS_OP maps to OPERATOR key`() {
        assertHighlightsTo(HurlTokenTypes.NOT_EQUALS_OP, HurlSyntaxHighlighter.OPERATOR)
    }

    @Test
    fun `test GREATER_THAN_OP maps to OPERATOR key`() {
        assertHighlightsTo(HurlTokenTypes.GREATER_THAN_OP, HurlSyntaxHighlighter.OPERATOR)
    }

    @Test
    fun `test GREATER_THAN_OR_EQUALS_OP maps to OPERATOR key`() {
        assertHighlightsTo(HurlTokenTypes.GREATER_THAN_OR_EQUALS_OP, HurlSyntaxHighlighter.OPERATOR)
    }

    @Test
    fun `test LESS_THAN_OP maps to OPERATOR key`() {
        assertHighlightsTo(HurlTokenTypes.LESS_THAN_OP, HurlSyntaxHighlighter.OPERATOR)
    }

    @Test
    fun `test LESS_THAN_OR_EQUALS_OP maps to OPERATOR key`() {
        assertHighlightsTo(HurlTokenTypes.LESS_THAN_OR_EQUALS_OP, HurlSyntaxHighlighter.OPERATOR)
    }

    // --- Brace tokens ---

    @Test
    fun `test LBRACE maps to BRACE key`() {
        assertHighlightsTo(HurlTokenTypes.LBRACE, HurlSyntaxHighlighter.BRACE)
    }

    @Test
    fun `test RBRACE maps to BRACE key`() {
        assertHighlightsTo(HurlTokenTypes.RBRACE, HurlSyntaxHighlighter.BRACE)
    }

    @Test
    fun `test LBRACKET maps to BRACE key`() {
        assertHighlightsTo(HurlTokenTypes.LBRACKET, HurlSyntaxHighlighter.BRACE)
    }

    @Test
    fun `test RBRACKET maps to BRACE key`() {
        assertHighlightsTo(HurlTokenTypes.RBRACKET, HurlSyntaxHighlighter.BRACE)
    }

    // --- JSON ---

    @Test
    fun `test JSON_TEXT maps to JSON key`() {
        assertHighlightsTo(HurlTokenTypes.JSON_TEXT, HurlSyntaxHighlighter.JSON)
    }

    // --- BAD_CHARACTER ---

    @Test
    fun `test BAD_CHARACTER maps to BAD_CHARACTER key`() {
        assertHighlightsTo(HurlTokenTypes.BAD_CHARACTER, HurlSyntaxHighlighter.BAD_CHARACTER)
    }

    // --- Empty mapping ---

    @Test
    fun `test unknown token returns empty keys`() {
        val keys = highlights(HurlTokenTypes.NEWLINE)
        assertEquals(0, keys.size, "NEWLINE should map to empty keys")
    }

    @Test
    fun `test WHITE_SPACE returns empty keys`() {
        val keys = highlights(HurlTokenTypes.WHITE_SPACE)
        assertEquals(0, keys.size, "WHITE_SPACE should map to empty keys")
    }
}
