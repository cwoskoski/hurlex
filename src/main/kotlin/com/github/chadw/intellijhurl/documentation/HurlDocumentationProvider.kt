package com.github.chadw.intellijhurl.documentation

import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.psi.PsiElement

class HurlDocumentationProvider : AbstractDocumentationProvider() {

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val target = originalElement ?: element ?: return null
        val tokenType = target.node?.elementType ?: return null

        return DOCUMENTATION[tokenType]
    }

    companion object {
        private val DOCUMENTATION = mapOf(
            // HTTP Methods
            HurlTokenTypes.GET to doc("GET", "Retrieve a representation of the specified resource."),
            HurlTokenTypes.POST to doc("POST", "Submit data to the specified resource, often causing a state change."),
            HurlTokenTypes.PUT to doc("PUT", "Replace the target resource with the request payload."),
            HurlTokenTypes.DELETE to doc("DELETE", "Delete the specified resource."),
            HurlTokenTypes.PATCH to doc("PATCH", "Apply partial modifications to a resource."),
            HurlTokenTypes.HEAD to doc("HEAD", "Same as GET but only returns headers, no body."),
            HurlTokenTypes.OPTIONS to doc("OPTIONS", "Describe the communication options for the target resource."),

            // Sections
            HurlTokenTypes.SECTION_QUERY_STRING_PARAMS to doc("[QueryStringParams]", "Define URL query string parameters as key-value pairs."),
            HurlTokenTypes.SECTION_FORM_PARAMS to doc("[FormParams]", "Define form URL-encoded parameters (application/x-www-form-urlencoded)."),
            HurlTokenTypes.SECTION_MULTIPART_FORM_DATA to doc("[MultipartFormData]", "Define multipart form data fields and file uploads."),
            HurlTokenTypes.SECTION_COOKIES to doc("[Cookies]", "Define cookies to send with the request."),
            HurlTokenTypes.SECTION_BASIC_AUTH to doc("[BasicAuth]", "Define HTTP Basic Authentication credentials (username:password)."),
            HurlTokenTypes.SECTION_OPTIONS to doc("[Options]", "Set request-level options (retry, verbose, insecure, etc.)."),
            HurlTokenTypes.SECTION_CAPTURES to doc("[Captures]", "Capture values from the response into variables for use in subsequent requests."),
            HurlTokenTypes.SECTION_ASSERTS to doc("[Asserts]", "Define assertions to validate the response (status, headers, body, etc.)."),

            // Query Keywords
            HurlTokenTypes.KW_STATUS to doc("status", "Query the HTTP response status code (e.g., 200, 404)."),
            HurlTokenTypes.KW_URL to doc("url", "Query the effective URL after redirections."),
            HurlTokenTypes.KW_HEADER to doc("header", "Query a response header value by name."),
            HurlTokenTypes.KW_COOKIE to doc("cookie", "Query a response cookie value by name."),
            HurlTokenTypes.KW_BODY to doc("body", "Query the raw response body as a string."),
            HurlTokenTypes.KW_XPATH to doc("xpath", "Query an XML/HTML response body using an XPath expression."),
            HurlTokenTypes.KW_JSONPATH to doc("jsonpath", "Query a JSON response body using a JSONPath expression."),
            HurlTokenTypes.KW_REGEX to doc("regex", "Query the response body using a regular expression."),
            HurlTokenTypes.KW_VARIABLE to doc("variable", "Query the value of a previously captured variable."),
            HurlTokenTypes.KW_DURATION to doc("duration", "Query the response time in milliseconds."),
            HurlTokenTypes.KW_SHA256 to doc("sha256", "Query the SHA-256 hash of the response body."),
            HurlTokenTypes.KW_MD5 to doc("md5", "Query the MD5 hash of the response body."),
            HurlTokenTypes.KW_BYTES to doc("bytes", "Query the raw response body as bytes."),
            HurlTokenTypes.KW_CERTIFICATE to doc("certificate", "Query a field from the SSL certificate."),

            // Predicate Keywords
            HurlTokenTypes.KW_EQUALS to doc("equals", "Assert that the query result equals the expected value."),
            HurlTokenTypes.KW_NOT_EQUALS to doc("notEquals", "Assert that the query result does not equal the value."),
            HurlTokenTypes.KW_GREATER_THAN to doc("greaterThan", "Assert that the query result is greater than the value."),
            HurlTokenTypes.KW_GREATER_THAN_OR_EQUALS to doc("greaterThanOrEquals", "Assert that the query result is greater than or equal to the value."),
            HurlTokenTypes.KW_LESS_THAN to doc("lessThan", "Assert that the query result is less than the value."),
            HurlTokenTypes.KW_LESS_THAN_OR_EQUALS to doc("lessThanOrEquals", "Assert that the query result is less than or equal to the value."),
            HurlTokenTypes.KW_STARTS_WITH to doc("startsWith", "Assert that the string query result starts with the value."),
            HurlTokenTypes.KW_ENDS_WITH to doc("endsWith", "Assert that the string query result ends with the value."),
            HurlTokenTypes.KW_CONTAINS to doc("contains", "Assert that the string query result contains the value."),
            HurlTokenTypes.KW_INCLUDES to doc("includes", "Assert that the collection query result includes the value."),
            HurlTokenTypes.KW_MATCHES to doc("matches", "Assert that the string query result matches the regex pattern."),
            HurlTokenTypes.KW_EXISTS to doc("exists", "Assert that the query result exists (is defined)."),
            HurlTokenTypes.KW_IS_EMPTY to doc("isEmpty", "Assert that the query result is empty."),
            HurlTokenTypes.KW_IS_NUMBER to doc("isNumber", "Assert that the query result is a number."),
            HurlTokenTypes.KW_IS_STRING to doc("isString", "Assert that the query result is a string."),
            HurlTokenTypes.KW_IS_BOOLEAN to doc("isBoolean", "Assert that the query result is a boolean."),
            HurlTokenTypes.KW_IS_COLLECTION to doc("isCollection", "Assert that the query result is a collection (array/list)."),
            HurlTokenTypes.KW_IS_DATE to doc("isDate", "Assert that the query result is a date."),
            HurlTokenTypes.KW_IS_ISO_DATE to doc("isIsoDate", "Assert that the query result is an ISO 8601 date string."),
            HurlTokenTypes.KW_IS_FLOAT to doc("isFloat", "Assert that the query result is a floating-point number."),
            HurlTokenTypes.KW_IS_INTEGER to doc("isInteger", "Assert that the query result is an integer."),

            // Filter Keywords
            HurlTokenTypes.KW_COUNT to doc("count", "Return the count (length) of a collection."),
            HurlTokenTypes.KW_NTH to doc("nth", "Return the nth element of a collection (0-based)."),
            HurlTokenTypes.KW_REPLACE to doc("replace", "Replace occurrences of a pattern in a string."),
            HurlTokenTypes.KW_SPLIT to doc("split", "Split a string by a separator into a collection."),
            HurlTokenTypes.KW_TO_DATE to doc("toDate", "Convert a string to a date using the given format."),
            HurlTokenTypes.KW_TO_FLOAT to doc("toFloat", "Convert a value to a floating-point number."),
            HurlTokenTypes.KW_TO_INT to doc("toInt", "Convert a value to an integer."),
            HurlTokenTypes.KW_DECODE to doc("decode", "Decode a byte sequence using the given encoding."),
            HurlTokenTypes.KW_FORMAT to doc("format", "Format a value using the given format string."),
            HurlTokenTypes.KW_HTML_ESCAPE to doc("htmlEscape", "HTML-escape special characters in a string."),
            HurlTokenTypes.KW_HTML_UNESCAPE to doc("htmlUnescape", "Unescape HTML entities in a string."),
            HurlTokenTypes.KW_URL_ENCODE to doc("urlEncode", "URL-encode a string."),
            HurlTokenTypes.KW_URL_DECODE to doc("urlDecode", "URL-decode a string."),

            // Option Keywords
            HurlTokenTypes.KW_RETRY to doc("retry", "Number of retries for a failed request (default: 0)."),
            HurlTokenTypes.KW_RETRY_INTERVAL to doc("retry-interval", "Interval between retries in milliseconds (default: 1000)."),
            HurlTokenTypes.KW_VERBOSE to doc("verbose", "Enable verbose output for this request."),
            HurlTokenTypes.KW_VERY_VERBOSE to doc("very-verbose", "Enable very verbose output (includes request/response body)."),
            HurlTokenTypes.KW_INSECURE to doc("insecure", "Allow insecure SSL connections (skip certificate verification)."),
            HurlTokenTypes.KW_LOCATION to doc("location", "Follow HTTP redirections (3xx responses)."),
            HurlTokenTypes.KW_LOCATION_TRUSTED to doc("location-trusted", "Follow redirections and send authentication to other hosts."),
            HurlTokenTypes.KW_COMPRESSED to doc("compressed", "Request a compressed response (gzip, deflate, br)."),
            HurlTokenTypes.KW_DELAY to doc("delay", "Add a delay in milliseconds before executing this request."),
            HurlTokenTypes.KW_MAX_REDIRS to doc("max-redirs", "Maximum number of redirections to follow (default: 50)."),
            HurlTokenTypes.KW_SKIP to doc("skip", "Skip this request entry (do not execute)."),
        )

        private fun doc(keyword: String, description: String): String {
            return "<div><b>$keyword</b></div><p>$description</p><p><a href=\"https://hurl.dev/docs/manual.html\">Hurl Documentation</a></p>"
        }
    }
}
