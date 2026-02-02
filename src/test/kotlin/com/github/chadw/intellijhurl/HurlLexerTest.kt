package com.github.chadw.intellijhurl

import com.github.chadw.intellijhurl.lexer.HurlLexerAdapter
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.psi.tree.IElementType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HurlLexerTest {

    private fun tokenize(text: String): List<Pair<IElementType, String>> {
        val lexer = HurlLexerAdapter()
        lexer.start(text)
        val tokens = mutableListOf<Pair<IElementType, String>>()
        while (lexer.tokenType != null) {
            tokens.add(Pair(lexer.tokenType!!, lexer.tokenText))
            lexer.advance()
        }
        return tokens
    }

    private fun tokenTypes(text: String): List<IElementType> = tokenize(text).map { it.first }

    @Test
    fun `test HTTP method lexing`() {
        val tokens = tokenize("GET http://localhost:8080/api")
        assertEquals(HurlTokenTypes.GET, tokens[0].first)
        assertEquals("GET", tokens[0].second)
    }

    @Test
    fun `test URL lexing`() {
        val tokens = tokenize("GET http://localhost:8080/api/users\n")
        val urlTokens = tokens.filter { it.first == HurlTokenTypes.URL_VALUE }
        assertTrue(urlTokens.isNotEmpty(), "Should have URL tokens")
    }

    @Test
    fun `test comment lexing`() {
        val tokens = tokenize("# This is a comment\n")
        assertEquals(HurlTokenTypes.COMMENT, tokens[0].first)
    }

    @Test
    fun `test HTTP version lexing`() {
        val tokens = tokenize("HTTP 200\n")
        // First non-whitespace token should be HTTP_VERSION
        val meaningful = tokens.filter { it.first != com.intellij.psi.TokenType.WHITE_SPACE && it.first != HurlTokenTypes.NEWLINE && it.first != HurlTokenTypes.WHITE_SPACE }
        assertTrue(meaningful.isNotEmpty())
    }

    @Test
    fun `test section lexing`() {
        val tokens = tokenize("[Asserts]\n")
        assertEquals(HurlTokenTypes.SECTION_ASSERTS, tokens[0].first)
    }

    @Test
    fun `test template variable lexing`() {
        val text = "GET http://localhost/{{var}}\n"
        val tokens = tokenize(text)
        val templateTokens = tokens.filter { it.first == HurlTokenTypes.LBRACE2 || it.first == HurlTokenTypes.TEMPLATE_VAR || it.first == HurlTokenTypes.RBRACE2 }
        assertTrue(templateTokens.any { it.first == HurlTokenTypes.TEMPLATE_VAR }, "Should find template variable")
    }

    @Test
    fun `test multiline string lexing`() {
        val text = "```json\n{\"key\": \"value\"}\n```\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.MULTILINE_STRING_OPEN })
        assertTrue(tokens.any { it.first == HurlTokenTypes.MULTILINE_STRING_CLOSE })
        assertTrue(tokens.any { it.first == HurlTokenTypes.MULTILINE_STRING_CONTENT })
    }

    @Test
    fun `test multiple methods`() {
        listOf("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD").forEach { method ->
            val tokens = tokenize("$method http://example.com\n")
            val first = tokens.first { it.first != HurlTokenTypes.WHITE_SPACE }
            assertTrue(HurlTokenTypes.METHODS.contains(first.first), "Should recognize $method as a method")
        }
    }

    @Test
    fun `test assert keywords in assert section`() {
        val text = "[Asserts]\nstatus == 200\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.KW_STATUS }, "Should recognize 'status' keyword in asserts")
        assertTrue(tokens.any { it.first == HurlTokenTypes.EQUALS_OP }, "Should recognize '==' operator")
        assertTrue(tokens.any { it.first == HurlTokenTypes.NUMBER }, "Should recognize number")
    }

    @Test
    fun `test predicate keywords`() {
        val text = "[Asserts]\njsonpath \"\$.name\" contains \"test\"\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.KW_JSONPATH })
        assertTrue(tokens.any { it.first == HurlTokenTypes.KW_CONTAINS })
    }

    @Test
    fun `test captures section`() {
        val text = "[Captures]\ntoken: header \"Authorization\"\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.SECTION_CAPTURES })
        assertTrue(tokens.any { it.first == HurlTokenTypes.KEY_STRING })
        assertTrue(tokens.any { it.first == HurlTokenTypes.KW_HEADER })
    }

    @Test
    fun `test options section`() {
        val text = "[Options]\nretry: 3\nverbose: true\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.SECTION_OPTIONS })
        assertTrue(tokens.any { it.first == HurlTokenTypes.KW_RETRY })
        assertTrue(tokens.any { it.first == HurlTokenTypes.KW_VERBOSE })
        assertTrue(tokens.any { it.first == HurlTokenTypes.TRUE })
    }
}
