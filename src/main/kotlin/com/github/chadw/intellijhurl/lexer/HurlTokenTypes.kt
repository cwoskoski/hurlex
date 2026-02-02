package com.github.chadw.intellijhurl.lexer

import com.github.chadw.intellijhurl.language.HurlLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

class HurlTokenType(debugName: String) : IElementType(debugName, HurlLanguage)

class HurlElementType(debugName: String) : IElementType(debugName, HurlLanguage)

object HurlTokenTypes {
    // HTTP Methods
    @JvmField val GET = HurlTokenType("GET")
    @JvmField val POST = HurlTokenType("POST")
    @JvmField val PUT = HurlTokenType("PUT")
    @JvmField val DELETE = HurlTokenType("DELETE")
    @JvmField val PATCH = HurlTokenType("PATCH")
    @JvmField val HEAD = HurlTokenType("HEAD")
    @JvmField val OPTIONS = HurlTokenType("OPTIONS")
    @JvmField val CONNECT = HurlTokenType("CONNECT")
    @JvmField val TRACE = HurlTokenType("TRACE")
    @JvmField val LINK = HurlTokenType("LINK")
    @JvmField val UNLINK = HurlTokenType("UNLINK")
    @JvmField val PURGE = HurlTokenType("PURGE")
    @JvmField val LOCK = HurlTokenType("LOCK")
    @JvmField val UNLOCK = HurlTokenType("UNLOCK")
    @JvmField val PROPFIND = HurlTokenType("PROPFIND")
    @JvmField val PROPPATCH = HurlTokenType("PROPPATCH")
    @JvmField val COPY = HurlTokenType("COPY")
    @JvmField val MOVE = HurlTokenType("MOVE")
    @JvmField val MKCOL = HurlTokenType("MKCOL")
    @JvmField val REPORT = HurlTokenType("REPORT")
    @JvmField val SEARCH = HurlTokenType("SEARCH")

    // HTTP Version
    @JvmField val HTTP_VERSION = HurlTokenType("HTTP_VERSION")

    // URL
    @JvmField val URL_VALUE = HurlTokenType("URL_VALUE")

    // Status Code
    @JvmField val STATUS_CODE = HurlTokenType("STATUS_CODE")

    // Section Names
    @JvmField val SECTION_QUERY_STRING_PARAMS = HurlTokenType("[QueryStringParams]")
    @JvmField val SECTION_FORM_PARAMS = HurlTokenType("[FormParams]")
    @JvmField val SECTION_MULTIPART_FORM_DATA = HurlTokenType("[MultipartFormData]")
    @JvmField val SECTION_COOKIES = HurlTokenType("[Cookies]")
    @JvmField val SECTION_BASIC_AUTH = HurlTokenType("[BasicAuth]")
    @JvmField val SECTION_OPTIONS = HurlTokenType("[Options]")
    @JvmField val SECTION_CAPTURES = HurlTokenType("[Captures]")
    @JvmField val SECTION_ASSERTS = HurlTokenType("[Asserts]")

    // Query Keywords
    @JvmField val KW_STATUS = HurlTokenType("status")
    @JvmField val KW_URL = HurlTokenType("url")
    @JvmField val KW_HEADER = HurlTokenType("header")
    @JvmField val KW_COOKIE = HurlTokenType("cookie")
    @JvmField val KW_BODY = HurlTokenType("body")
    @JvmField val KW_XPATH = HurlTokenType("xpath")
    @JvmField val KW_JSONPATH = HurlTokenType("jsonpath")
    @JvmField val KW_REGEX = HurlTokenType("regex")
    @JvmField val KW_VARIABLE = HurlTokenType("variable")
    @JvmField val KW_DURATION = HurlTokenType("duration")
    @JvmField val KW_SHA256 = HurlTokenType("sha256")
    @JvmField val KW_MD5 = HurlTokenType("md5")
    @JvmField val KW_BYTES = HurlTokenType("bytes")
    @JvmField val KW_CERTIFICATE = HurlTokenType("certificate")

    // Predicate Keywords
    @JvmField val KW_EQUALS = HurlTokenType("equals")
    @JvmField val KW_NOT_EQUALS = HurlTokenType("notEquals")
    @JvmField val KW_GREATER_THAN = HurlTokenType("greaterThan")
    @JvmField val KW_GREATER_THAN_OR_EQUALS = HurlTokenType("greaterThanOrEquals")
    @JvmField val KW_LESS_THAN = HurlTokenType("lessThan")
    @JvmField val KW_LESS_THAN_OR_EQUALS = HurlTokenType("lessThanOrEquals")
    @JvmField val KW_STARTS_WITH = HurlTokenType("startsWith")
    @JvmField val KW_ENDS_WITH = HurlTokenType("endsWith")
    @JvmField val KW_CONTAINS = HurlTokenType("contains")
    @JvmField val KW_INCLUDES = HurlTokenType("includes")
    @JvmField val KW_MATCHES = HurlTokenType("matches")
    @JvmField val KW_EXISTS = HurlTokenType("exists")
    @JvmField val KW_IS_EMPTY = HurlTokenType("isEmpty")
    @JvmField val KW_IS_NUMBER = HurlTokenType("isNumber")
    @JvmField val KW_IS_STRING = HurlTokenType("isString")
    @JvmField val KW_IS_BOOLEAN = HurlTokenType("isBoolean")
    @JvmField val KW_IS_COLLECTION = HurlTokenType("isCollection")
    @JvmField val KW_IS_DATE = HurlTokenType("isDate")
    @JvmField val KW_IS_ISO_DATE = HurlTokenType("isIsoDate")
    @JvmField val KW_IS_FLOAT = HurlTokenType("isFloat")
    @JvmField val KW_IS_INTEGER = HurlTokenType("isInteger")
    @JvmField val KW_NOT = HurlTokenType("not")

    // Filter Keywords
    @JvmField val KW_COUNT = HurlTokenType("count")
    @JvmField val KW_NTH = HurlTokenType("nth")
    @JvmField val KW_REPLACE = HurlTokenType("replace")
    @JvmField val KW_SPLIT = HurlTokenType("split")
    @JvmField val KW_TO_DATE = HurlTokenType("toDate")
    @JvmField val KW_TO_FLOAT = HurlTokenType("toFloat")
    @JvmField val KW_TO_INT = HurlTokenType("toInt")
    @JvmField val KW_DECODE = HurlTokenType("decode")
    @JvmField val KW_FORMAT = HurlTokenType("format")
    @JvmField val KW_HTML_ESCAPE = HurlTokenType("htmlEscape")
    @JvmField val KW_HTML_UNESCAPE = HurlTokenType("htmlUnescape")
    @JvmField val KW_URL_ENCODE = HurlTokenType("urlEncode")
    @JvmField val KW_URL_DECODE = HurlTokenType("urlDecode")

    // Option Keys
    @JvmField val KW_AWS_SIGV4 = HurlTokenType("aws-sigv4")
    @JvmField val KW_CACERT = HurlTokenType("cacert")
    @JvmField val KW_CERT = HurlTokenType("cert")
    @JvmField val KW_COMPRESSED = HurlTokenType("compressed")
    @JvmField val KW_CONNECT_TO = HurlTokenType("connect-to")
    @JvmField val KW_DELAY = HurlTokenType("delay")
    @JvmField val KW_HTTP10 = HurlTokenType("http1.0")
    @JvmField val KW_HTTP11 = HurlTokenType("http1.1")
    @JvmField val KW_HTTP2 = HurlTokenType("http2")
    @JvmField val KW_HTTP3 = HurlTokenType("http3")
    @JvmField val KW_INSECURE = HurlTokenType("insecure")
    @JvmField val KW_IPVF = HurlTokenType("ipv4")
    @JvmField val KW_IPVS = HurlTokenType("ipv6")
    @JvmField val KW_KEY = HurlTokenType("key")
    @JvmField val KW_LOCATION = HurlTokenType("location")
    @JvmField val KW_LOCATION_TRUSTED = HurlTokenType("location-trusted")
    @JvmField val KW_MAX_REDIRS = HurlTokenType("max-redirs")
    @JvmField val KW_OUTPUT = HurlTokenType("output")
    @JvmField val KW_PATH_AS_IS = HurlTokenType("path-as-is")
    @JvmField val KW_PROXY = HurlTokenType("proxy")
    @JvmField val KW_RESOLVE = HurlTokenType("resolve")
    @JvmField val KW_RETRY = HurlTokenType("retry")
    @JvmField val KW_RETRY_INTERVAL = HurlTokenType("retry-interval")
    @JvmField val KW_SKIP = HurlTokenType("skip")
    @JvmField val KW_UNIX_SOCKET = HurlTokenType("unix-socket")
    @JvmField val KW_USER = HurlTokenType("user")
    @JvmField val KW_VERBOSE = HurlTokenType("verbose")
    @JvmField val KW_VERY_VERBOSE = HurlTokenType("very-verbose")

    // Literals
    @JvmField val TRUE = HurlTokenType("true")
    @JvmField val FALSE = HurlTokenType("false")
    @JvmField val NULL = HurlTokenType("null")
    @JvmField val NUMBER = HurlTokenType("NUMBER")
    @JvmField val FLOAT_NUMBER = HurlTokenType("FLOAT_NUMBER")

    // Strings
    @JvmField val QUOTED_STRING = HurlTokenType("QUOTED_STRING")
    @JvmField val BACKTICK_STRING = HurlTokenType("BACKTICK_STRING")
    @JvmField val MULTILINE_STRING_OPEN = HurlTokenType("MULTILINE_STRING_OPEN")
    @JvmField val MULTILINE_STRING_CONTENT = HurlTokenType("MULTILINE_STRING_CONTENT")
    @JvmField val MULTILINE_STRING_CLOSE = HurlTokenType("MULTILINE_STRING_CLOSE")
    @JvmField val ONELINE_STRING = HurlTokenType("ONELINE_STRING")
    @JvmField val KEY_STRING = HurlTokenType("KEY_STRING")
    @JvmField val VALUE_STRING = HurlTokenType("VALUE_STRING")
    @JvmField val FILENAME = HurlTokenType("FILENAME")

    // Template
    @JvmField val LBRACE2 = HurlTokenType("{{")
    @JvmField val RBRACE2 = HurlTokenType("}}")
    @JvmField val TEMPLATE_VAR = HurlTokenType("TEMPLATE_VAR")

    // Punctuation
    @JvmField val COLON = HurlTokenType(":")
    @JvmField val SEMICOLON = HurlTokenType(";")
    @JvmField val COMMA = HurlTokenType(",")
    @JvmField val EQUALS_OP = HurlTokenType("==")
    @JvmField val NOT_EQUALS_OP = HurlTokenType("!=")
    @JvmField val GREATER_THAN_OP = HurlTokenType(">")
    @JvmField val GREATER_THAN_OR_EQUALS_OP = HurlTokenType(">=")
    @JvmField val LESS_THAN_OP = HurlTokenType("<")
    @JvmField val LESS_THAN_OR_EQUALS_OP = HurlTokenType("<=")
    @JvmField val LBRACE = HurlTokenType("{")
    @JvmField val RBRACE = HurlTokenType("}")
    @JvmField val LBRACKET = HurlTokenType("[")
    @JvmField val RBRACKET = HurlTokenType("]")
    @JvmField val LPAREN = HurlTokenType("(")
    @JvmField val RPAREN = HurlTokenType(")")
    @JvmField val DOT = HurlTokenType(".")
    @JvmField val STAR = HurlTokenType("*")

    // Misc
    @JvmField val COMMENT = HurlTokenType("COMMENT")
    @JvmField val NEWLINE = HurlTokenType("NEWLINE")
    @JvmField val WHITE_SPACE = HurlTokenType("WHITE_SPACE")
    @JvmField val BAD_CHARACTER = HurlTokenType("BAD_CHARACTER")
    @JvmField val HEADER_KEY = HurlTokenType("HEADER_KEY")
    @JvmField val HEADER_VALUE = HurlTokenType("HEADER_VALUE")
    @JvmField val JSON_TEXT = HurlTokenType("JSON_TEXT")
    @JvmField val XML_TEXT = HurlTokenType("XML_TEXT")
    @JvmField val BODY_DATA = HurlTokenType("BODY_DATA")
    @JvmField val FILE_PREFIX = HurlTokenType("file,")
    @JvmField val BASE64_PREFIX = HurlTokenType("base64,")
    @JvmField val BASE64_DATA = HurlTokenType("BASE64_DATA")
    @JvmField val HEX_PREFIX = HurlTokenType("hex,")
    @JvmField val HEX_DATA = HurlTokenType("HEX_DATA")

    // Token sets for parser
    @JvmField val METHODS = TokenSet.create(
        GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, CONNECT, TRACE,
        LINK, UNLINK, PURGE, LOCK, UNLOCK, PROPFIND, PROPPATCH,
        COPY, MOVE, MKCOL, REPORT, SEARCH
    )

    @JvmField val SECTIONS = TokenSet.create(
        SECTION_QUERY_STRING_PARAMS, SECTION_FORM_PARAMS, SECTION_MULTIPART_FORM_DATA,
        SECTION_COOKIES, SECTION_BASIC_AUTH, SECTION_OPTIONS,
        SECTION_CAPTURES, SECTION_ASSERTS
    )

    @JvmField val QUERY_KEYWORDS = TokenSet.create(
        KW_STATUS, KW_URL, KW_HEADER, KW_COOKIE, KW_BODY, KW_XPATH,
        KW_JSONPATH, KW_REGEX, KW_VARIABLE, KW_DURATION, KW_SHA256,
        KW_MD5, KW_BYTES, KW_CERTIFICATE
    )

    @JvmField val PREDICATE_KEYWORDS = TokenSet.create(
        KW_EQUALS, KW_NOT_EQUALS, KW_GREATER_THAN, KW_GREATER_THAN_OR_EQUALS,
        KW_LESS_THAN, KW_LESS_THAN_OR_EQUALS, KW_STARTS_WITH, KW_ENDS_WITH,
        KW_CONTAINS, KW_INCLUDES, KW_MATCHES, KW_EXISTS, KW_IS_EMPTY,
        KW_IS_NUMBER, KW_IS_STRING, KW_IS_BOOLEAN, KW_IS_COLLECTION,
        KW_IS_DATE, KW_IS_ISO_DATE, KW_IS_FLOAT, KW_IS_INTEGER, KW_NOT
    )

    @JvmField val FILTER_KEYWORDS = TokenSet.create(
        KW_COUNT, KW_NTH, KW_REPLACE, KW_SPLIT, KW_TO_DATE, KW_TO_FLOAT,
        KW_TO_INT, KW_DECODE, KW_FORMAT, KW_HTML_ESCAPE, KW_HTML_UNESCAPE,
        KW_URL_ENCODE, KW_URL_DECODE
    )

    @JvmField val STRINGS = TokenSet.create(
        QUOTED_STRING, BACKTICK_STRING, ONELINE_STRING
    )

    @JvmField val COMMENTS = TokenSet.create(COMMENT)
    @JvmField val WHITESPACES = TokenSet.create(WHITE_SPACE)
    @JvmField val NEWLINES = TokenSet.create(NEWLINE)
}
