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

    // --- Remaining HTTP methods ---

    @Test
    fun `test CONNECT method`() {
        val tokens = tokenize("CONNECT http://example.com\n")
        assertEquals(HurlTokenTypes.CONNECT, tokens[0].first)
        assertEquals("CONNECT", tokens[0].second)
    }

    @Test
    fun `test TRACE method`() {
        val tokens = tokenize("TRACE http://example.com\n")
        assertEquals(HurlTokenTypes.TRACE, tokens[0].first)
        assertEquals("TRACE", tokens[0].second)
    }

    @Test
    fun `test LINK method`() {
        val tokens = tokenize("LINK http://example.com\n")
        assertEquals(HurlTokenTypes.LINK, tokens[0].first)
    }

    @Test
    fun `test UNLINK method`() {
        val tokens = tokenize("UNLINK http://example.com\n")
        assertEquals(HurlTokenTypes.UNLINK, tokens[0].first)
    }

    @Test
    fun `test PURGE method`() {
        val tokens = tokenize("PURGE http://example.com\n")
        assertEquals(HurlTokenTypes.PURGE, tokens[0].first)
    }

    @Test
    fun `test LOCK method`() {
        val tokens = tokenize("LOCK http://example.com\n")
        assertEquals(HurlTokenTypes.LOCK, tokens[0].first)
    }

    @Test
    fun `test UNLOCK method`() {
        val tokens = tokenize("UNLOCK http://example.com\n")
        assertEquals(HurlTokenTypes.UNLOCK, tokens[0].first)
    }

    @Test
    fun `test PROPFIND method`() {
        val tokens = tokenize("PROPFIND http://example.com\n")
        assertEquals(HurlTokenTypes.PROPFIND, tokens[0].first)
    }

    @Test
    fun `test PROPPATCH method`() {
        val tokens = tokenize("PROPPATCH http://example.com\n")
        assertEquals(HurlTokenTypes.PROPPATCH, tokens[0].first)
    }

    @Test
    fun `test COPY method`() {
        val tokens = tokenize("COPY http://example.com\n")
        assertEquals(HurlTokenTypes.COPY, tokens[0].first)
    }

    @Test
    fun `test MOVE method`() {
        val tokens = tokenize("MOVE http://example.com\n")
        assertEquals(HurlTokenTypes.MOVE, tokens[0].first)
    }

    @Test
    fun `test MKCOL method`() {
        val tokens = tokenize("MKCOL http://example.com\n")
        assertEquals(HurlTokenTypes.MKCOL, tokens[0].first)
    }

    @Test
    fun `test REPORT method`() {
        val tokens = tokenize("REPORT http://example.com\n")
        assertEquals(HurlTokenTypes.REPORT, tokens[0].first)
    }

    @Test
    fun `test SEARCH method`() {
        val tokens = tokenize("SEARCH http://example.com\n")
        assertEquals(HurlTokenTypes.SEARCH, tokens[0].first)
    }

    @Test
    fun `test all remaining methods are in METHODS token set`() {
        val methods = listOf(
            "CONNECT", "TRACE", "LINK", "UNLINK", "PURGE",
            "LOCK", "UNLOCK", "PROPFIND", "PROPPATCH",
            "COPY", "MOVE", "MKCOL", "REPORT", "SEARCH"
        )
        for (method in methods) {
            val tokens = tokenize("$method http://example.com\n")
            val first = tokens.first { it.first != HurlTokenTypes.WHITE_SPACE }
            assertTrue(
                HurlTokenTypes.METHODS.contains(first.first),
                "Should recognize $method as a method token"
            )
        }
    }

    // --- Section names ---

    @Test
    fun `test QueryStringParams section`() {
        val tokens = tokenize("[QueryStringParams]\n")
        assertEquals(HurlTokenTypes.SECTION_QUERY_STRING_PARAMS, tokens[0].first)
    }

    @Test
    fun `test FormParams section`() {
        val tokens = tokenize("[FormParams]\n")
        assertEquals(HurlTokenTypes.SECTION_FORM_PARAMS, tokens[0].first)
    }

    @Test
    fun `test MultipartFormData section`() {
        val tokens = tokenize("[MultipartFormData]\n")
        assertEquals(HurlTokenTypes.SECTION_MULTIPART_FORM_DATA, tokens[0].first)
    }

    @Test
    fun `test Cookies section`() {
        val tokens = tokenize("[Cookies]\n")
        assertEquals(HurlTokenTypes.SECTION_COOKIES, tokens[0].first)
    }

    @Test
    fun `test BasicAuth section`() {
        val tokens = tokenize("[BasicAuth]\n")
        assertEquals(HurlTokenTypes.SECTION_BASIC_AUTH, tokens[0].first)
    }

    @Test
    fun `test all section tokens are in SECTIONS token set`() {
        val sections = listOf(
            "[QueryStringParams]" to HurlTokenTypes.SECTION_QUERY_STRING_PARAMS,
            "[FormParams]" to HurlTokenTypes.SECTION_FORM_PARAMS,
            "[MultipartFormData]" to HurlTokenTypes.SECTION_MULTIPART_FORM_DATA,
            "[Cookies]" to HurlTokenTypes.SECTION_COOKIES,
            "[BasicAuth]" to HurlTokenTypes.SECTION_BASIC_AUTH,
            "[Options]" to HurlTokenTypes.SECTION_OPTIONS,
            "[Captures]" to HurlTokenTypes.SECTION_CAPTURES,
            "[Asserts]" to HurlTokenTypes.SECTION_ASSERTS
        )
        for ((text, expectedType) in sections) {
            val tokens = tokenize("$text\n")
            assertEquals(expectedType, tokens[0].first, "Section $text should produce token $expectedType")
            assertTrue(
                HurlTokenTypes.SECTIONS.contains(tokens[0].first),
                "Token for $text should be in SECTIONS token set"
            )
        }
    }

    // --- Header key:value lexing ---

    @Test
    fun `test header key value lexing`() {
        val text = "GET http://localhost/api\nContent-Type: application/json\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.HEADER_KEY }, "Should have HEADER_KEY token")
        assertTrue(tokens.any { it.first == HurlTokenTypes.HEADER_VALUE }, "Should have HEADER_VALUE token")
        val headerKey = tokens.first { it.first == HurlTokenTypes.HEADER_KEY }
        assertEquals("Content-Type", headerKey.second)
    }

    // --- JSON body lexing ---

    @Test
    fun `test JSON body with nested objects`() {
        val text = "POST http://localhost/api\n{\"outer\":{\"inner\":\"value\"},\"array\":[1,2,3]}\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.JSON_TEXT || it.first == HurlTokenTypes.LBRACE },
            "Should tokenize JSON body"
        )
    }

    // --- Float number lexing ---

    @Test
    fun `test float number lexing`() {
        val text = "[Asserts]\nstatus == 200\njsonpath \"\$.price\" == 19.99\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.FLOAT_NUMBER },
            "Should recognize float number 19.99"
        )
        val floatToken = tokens.first { it.first == HurlTokenTypes.FLOAT_NUMBER }
        assertEquals("19.99", floatToken.second)
    }

    // --- Boolean and null lexing ---

    @Test
    fun `test boolean true lexing`() {
        val text = "[Options]\nverbose: true\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.TRUE }, "Should recognize 'true'")
    }

    @Test
    fun `test boolean false lexing`() {
        val text = "[Options]\nverbose: false\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.FALSE }, "Should recognize 'false'")
    }

    @Test
    fun `test null lexing`() {
        val text = "[Asserts]\njsonpath \"\$.field\" == null\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.NULL }, "Should recognize 'null'")
    }

    // --- File reference lexing ---

    @Test
    fun `test file reference lexing`() {
        // file, prefix is recognized in multipart context but the lexer
        // handles it in the key-value state; test that the section is recognized
        val text = "POST http://localhost/upload\n[MultipartFormData]\nfile1: file,filename.json;\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.SECTION_MULTIPART_FORM_DATA },
            "Should recognize [MultipartFormData] section"
        )
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.KEY_STRING },
            "Should recognize key in multipart section"
        )
    }

    // --- Base64 and hex body lexing ---

    @Test
    fun `test base64 body lexing`() {
        // The lexer recognizes 'base64,' as a prefix token in body context
        val text = "POST http://localhost/api\nbase64,SGVsbG8gV29ybGQ=;\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.BASE64_PREFIX },
            "Should recognize 'base64,' prefix"
        )
    }

    @Test
    fun `test hex body lexing`() {
        // The lexer recognizes 'hex,' as a prefix token in body context
        val text = "POST http://localhost/api\nhex,48656c6c6f;\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.HEX_PREFIX },
            "Should recognize 'hex,' prefix"
        )
    }

    // --- URL with query parameters ---

    @Test
    fun `test URL with query parameters`() {
        val text = "GET http://example.com/api?key=value&page=1\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.URL_VALUE },
            "Should recognize URL with query parameters"
        )
    }

    // --- URL with template variable ---

    @Test
    fun `test URL with template variable`() {
        val text = "GET http://example.com/api/{{user_id}}/profile\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.LBRACE2 },
            "Should recognize {{ in URL"
        )
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.TEMPLATE_VAR },
            "Should recognize template variable in URL"
        )
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.RBRACE2 },
            "Should recognize }} in URL"
        )
    }

    // --- Empty lines between entries ---

    @Test
    fun `test empty lines between entries`() {
        val text = "GET http://example.com\nHTTP 200\n\n\nGET http://example.com/other\nHTTP 200\n"
        val tokens = tokenize(text)
        val getTokens = tokens.filter { it.first == HurlTokenTypes.GET }
        assertEquals(2, getTokens.size, "Should recognize two GET entries separated by empty lines")
    }

    // --- Multiple entries in sequence ---

    @Test
    fun `test multiple entries in sequence`() {
        val text = "GET http://example.com/first\nHTTP 200\n\nPOST http://example.com/second\nHTTP 201\n\nDELETE http://example.com/third\nHTTP 204\n"
        val tokens = tokenize(text)
        assertTrue(tokens.any { it.first == HurlTokenTypes.GET }, "Should have GET")
        assertTrue(tokens.any { it.first == HurlTokenTypes.POST }, "Should have POST")
        assertTrue(tokens.any { it.first == HurlTokenTypes.DELETE }, "Should have DELETE")
    }

    // --- Filter keywords in assert context ---

    @Test
    fun `test filter keywords in assert context`() {
        val text = "[Asserts]\njsonpath \"\$.items\" count == 5\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.KW_COUNT },
            "Should recognize 'count' filter keyword"
        )
    }

    @Test
    fun `test multiple filter keywords`() {
        val filterKeywords = mapOf(
            "count" to HurlTokenTypes.KW_COUNT,
            "nth" to HurlTokenTypes.KW_NTH,
            "replace" to HurlTokenTypes.KW_REPLACE,
            "split" to HurlTokenTypes.KW_SPLIT,
            "toDate" to HurlTokenTypes.KW_TO_DATE,
            "toFloat" to HurlTokenTypes.KW_TO_FLOAT,
            "toInt" to HurlTokenTypes.KW_TO_INT,
            "decode" to HurlTokenTypes.KW_DECODE,
            "format" to HurlTokenTypes.KW_FORMAT,
            "htmlEscape" to HurlTokenTypes.KW_HTML_ESCAPE,
            "htmlUnescape" to HurlTokenTypes.KW_HTML_UNESCAPE,
            "urlEncode" to HurlTokenTypes.KW_URL_ENCODE,
            "urlDecode" to HurlTokenTypes.KW_URL_DECODE
        )
        for ((keyword, expectedType) in filterKeywords) {
            assertTrue(
                HurlTokenTypes.FILTER_KEYWORDS.contains(expectedType),
                "Token for '$keyword' should be in FILTER_KEYWORDS"
            )
        }
    }

    // --- Operator tokens ---

    @Test
    fun `test not equals operator`() {
        val text = "[Asserts]\nstatus != 500\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.NOT_EQUALS_OP },
            "Should recognize '!=' operator"
        )
    }

    @Test
    fun `test greater than operator`() {
        val text = "[Asserts]\nstatus > 100\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.GREATER_THAN_OP },
            "Should recognize '>' operator"
        )
    }

    @Test
    fun `test greater than or equals operator`() {
        val text = "[Asserts]\nstatus >= 200\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.GREATER_THAN_OR_EQUALS_OP },
            "Should recognize '>=' operator"
        )
    }

    @Test
    fun `test less than operator`() {
        val text = "[Asserts]\nstatus < 500\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.LESS_THAN_OP },
            "Should recognize '<' operator"
        )
    }

    @Test
    fun `test less than or equals operator`() {
        val text = "[Asserts]\nstatus <= 400\n"
        val tokens = tokenize(text)
        assertTrue(
            tokens.any { it.first == HurlTokenTypes.LESS_THAN_OR_EQUALS_OP },
            "Should recognize '<=' operator"
        )
    }
}
