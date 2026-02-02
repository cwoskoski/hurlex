package com.github.chadw.intellijhurl.completion

import com.github.chadw.intellijhurl.language.HurlLanguage
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext

class HurlCompletionContributor : CompletionContributor() {

    init {
        // Method completion at line start
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().withLanguage(HurlLanguage),
            MethodCompletionProvider()
        )
    }

    private class MethodCompletionProvider : CompletionProvider<CompletionParameters>() {

        companion object {
            val HTTP_METHODS = listOf(
                "GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS",
                "CONNECT", "TRACE", "LINK", "UNLINK", "PURGE", "LOCK", "UNLOCK",
                "PROPFIND", "PROPPATCH", "COPY", "MOVE", "MKCOL", "REPORT", "SEARCH"
            )

            val SECTIONS = listOf(
                "[QueryStringParams]", "[FormParams]", "[MultipartFormData]",
                "[Cookies]", "[BasicAuth]", "[Options]", "[Captures]", "[Asserts]"
            )

            val QUERY_KEYWORDS = listOf(
                "status", "url", "header", "cookie", "body", "xpath", "jsonpath",
                "regex", "variable", "duration", "sha256", "md5", "bytes", "certificate"
            )

            val PREDICATE_KEYWORDS = listOf(
                "equals", "notEquals", "greaterThan", "greaterThanOrEquals",
                "lessThan", "lessThanOrEquals", "startsWith", "endsWith",
                "contains", "includes", "matches", "exists", "isEmpty",
                "isNumber", "isString", "isBoolean", "isCollection",
                "isDate", "isIsoDate", "isFloat", "isInteger", "not"
            )

            val FILTER_KEYWORDS = listOf(
                "count", "nth", "replace", "split", "toDate", "toFloat", "toInt",
                "decode", "format", "htmlEscape", "htmlUnescape", "urlEncode", "urlDecode"
            )

            val OPTION_KEYS = listOf(
                "aws-sigv4", "cacert", "cert", "compressed", "connect-to",
                "delay", "http1.0", "http1.1", "http2", "http3",
                "insecure", "ipv4", "ipv6", "key", "location", "location-trusted",
                "max-redirs", "output", "path-as-is", "proxy", "resolve",
                "retry", "retry-interval", "skip", "unix-socket", "user",
                "verbose", "very-verbose", "variable"
            )

            val COMMON_HEADERS = listOf(
                "Accept", "Accept-Charset", "Accept-Encoding", "Accept-Language",
                "Authorization", "Cache-Control", "Content-Disposition", "Content-Encoding",
                "Content-Length", "Content-Type", "Cookie", "Host", "If-Match",
                "If-Modified-Since", "If-None-Match", "Origin", "Referer",
                "User-Agent", "X-Requested-With"
            )

            val CONTENT_TYPES = listOf(
                "application/json", "application/xml", "application/x-www-form-urlencoded",
                "multipart/form-data", "text/plain", "text/html", "text/xml"
            )
        }

        override fun addCompletions(
            parameters: CompletionParameters,
            context: ProcessingContext,
            result: CompletionResultSet
        ) {
            val position = parameters.position
            val prevSibling = findPrevMeaningfulSibling(position)
            val prevType = prevSibling?.node?.elementType

            // Determine context from position
            val lineStart = isAtLineStart(parameters)
            val inAssertSection = isInSection(position, "ASSERT")
            val inCaptureSection = isInSection(position, "CAPTURE")
            val inOptionsSection = isInSection(position, "OPTIONS")
            val afterColon = prevType == HurlTokenTypes.COLON
            val afterQuery = prevType != null && HurlTokenTypes.QUERY_KEYWORDS.contains(prevType)

            if (lineStart) {
                // HTTP methods
                HTTP_METHODS.forEach {
                    result.addElement(LookupElementBuilder.create(it).bold().withTypeText("HTTP Method"))
                }
                // HTTP version for response
                result.addElement(LookupElementBuilder.create("HTTP").bold().withTypeText("Response"))
                // Sections
                SECTIONS.forEach {
                    result.addElement(LookupElementBuilder.create(it).bold().withTypeText("Section"))
                }
                // Common headers
                COMMON_HEADERS.forEach {
                    result.addElement(LookupElementBuilder.create("$it: ").withTypeText("Header"))
                }
            }

            if (inAssertSection || inCaptureSection) {
                QUERY_KEYWORDS.forEach {
                    result.addElement(LookupElementBuilder.create(it).withTypeText("Query"))
                }
                FILTER_KEYWORDS.forEach {
                    result.addElement(LookupElementBuilder.create(it).withTypeText("Filter"))
                }
            }

            if (inAssertSection) {
                PREDICATE_KEYWORDS.forEach {
                    result.addElement(LookupElementBuilder.create(it).withTypeText("Predicate"))
                }
                // Operator predicates
                listOf("==", "!=", ">", ">=", "<", "<=").forEach {
                    result.addElement(LookupElementBuilder.create(it).withTypeText("Operator"))
                }
            }

            if (inOptionsSection && lineStart) {
                OPTION_KEYS.forEach {
                    result.addElement(LookupElementBuilder.create("$it:").withTypeText("Option"))
                }
            }

            // Content-Type values after "Content-Type:"
            if (afterColon) {
                val keyText = prevSibling?.prevSibling?.text?.trim()
                if (keyText == "Content-Type") {
                    CONTENT_TYPES.forEach {
                        result.addElement(LookupElementBuilder.create(it).withTypeText("Content-Type"))
                    }
                }
            }

            // Boolean/null completions in value positions
            if (afterColon || afterQuery) {
                result.addElement(LookupElementBuilder.create("true").bold().withTypeText("Boolean"))
                result.addElement(LookupElementBuilder.create("false").bold().withTypeText("Boolean"))
                result.addElement(LookupElementBuilder.create("null").bold().withTypeText("Null"))
            }

            // Template variable completion
            val text = parameters.editor.document.text
            val templateVars = Regex("\\{\\{(\\w+)}}").findAll(text).map { it.groupValues[1] }.toSet()
            templateVars.forEach { varName ->
                result.addElement(LookupElementBuilder.create("{{$varName}}").withTypeText("Variable"))
            }
        }

        private fun isAtLineStart(parameters: CompletionParameters): Boolean {
            val offset = parameters.offset
            val document = parameters.editor.document
            val lineNumber = document.getLineNumber(offset)
            val lineStart = document.getLineStartOffset(lineNumber)
            val textBefore = document.getText(com.intellij.openapi.util.TextRange(lineStart, offset))
            return textBefore.isBlank() || textBefore.trim().length <= 2
        }

        private fun isInSection(element: PsiElement, sectionName: String): Boolean {
            var current: PsiElement? = element
            while (current != null) {
                val typeName = current.node?.elementType?.toString() ?: ""
                if (typeName.contains(sectionName, ignoreCase = true)) return true
                current = current.parent
            }
            return false
        }

        private fun findPrevMeaningfulSibling(element: PsiElement): PsiElement? {
            var sibling = element.prevSibling
            while (sibling != null) {
                val type = sibling.node?.elementType
                if (type != HurlTokenTypes.WHITE_SPACE && type != HurlTokenTypes.NEWLINE) {
                    return sibling
                }
                sibling = sibling.prevSibling
            }
            return null
        }
    }
}
