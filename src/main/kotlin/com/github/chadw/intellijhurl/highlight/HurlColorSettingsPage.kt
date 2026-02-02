package com.github.chadw.intellijhurl.highlight

import com.github.chadw.intellijhurl.language.HurlIcons
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class HurlColorSettingsPage : ColorSettingsPage {

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("HTTP Method", HurlSyntaxHighlighter.METHOD),
            AttributesDescriptor("URL", HurlSyntaxHighlighter.URL),
            AttributesDescriptor("HTTP Version", HurlSyntaxHighlighter.HTTP_VERSION),
            AttributesDescriptor("Status Code", HurlSyntaxHighlighter.STATUS_CODE),
            AttributesDescriptor("Section Name", HurlSyntaxHighlighter.SECTION),
            AttributesDescriptor("Query Keyword", HurlSyntaxHighlighter.QUERY_KEYWORD),
            AttributesDescriptor("Predicate Keyword", HurlSyntaxHighlighter.PREDICATE_KEYWORD),
            AttributesDescriptor("Filter Keyword", HurlSyntaxHighlighter.FILTER_KEYWORD),
            AttributesDescriptor("Option Keyword", HurlSyntaxHighlighter.OPTION_KEYWORD),
            AttributesDescriptor("String", HurlSyntaxHighlighter.STRING),
            AttributesDescriptor("Number", HurlSyntaxHighlighter.NUMBER),
            AttributesDescriptor("Boolean", HurlSyntaxHighlighter.BOOLEAN),
            AttributesDescriptor("Null", HurlSyntaxHighlighter.NULL),
            AttributesDescriptor("Comment", HurlSyntaxHighlighter.COMMENT),
            AttributesDescriptor("Header Key", HurlSyntaxHighlighter.HEADER_KEY),
            AttributesDescriptor("Header Value", HurlSyntaxHighlighter.HEADER_VALUE),
            AttributesDescriptor("Template Variable", HurlSyntaxHighlighter.TEMPLATE_VAR),
            AttributesDescriptor("Template Brace", HurlSyntaxHighlighter.TEMPLATE_BRACE),
            AttributesDescriptor("Operator", HurlSyntaxHighlighter.OPERATOR),
            AttributesDescriptor("Key", HurlSyntaxHighlighter.KEY),
            AttributesDescriptor("Value", HurlSyntaxHighlighter.VALUE),
            AttributesDescriptor("Multiline String", HurlSyntaxHighlighter.MULTILINE_STRING),
            AttributesDescriptor("JSON Body", HurlSyntaxHighlighter.JSON),
            AttributesDescriptor("Brace", HurlSyntaxHighlighter.BRACE),
        )
    }

    override fun getIcon(): Icon = HurlIcons.FILE

    override fun getHighlighter(): SyntaxHighlighter = HurlSyntaxHighlighter()

    override fun getDemoText(): String = """
# This is a comment
GET http://localhost:8080/api/users
Content-Type: application/json
[QueryStringParams]
page: 1
limit: 10

HTTP 200
[Captures]
token: header "Authorization"
user_id: jsonpath "${'$'}.users[0].id"

[Asserts]
status == 200
header "Content-Type" contains "json"
jsonpath "${'$'}.users" count == 10
body contains "success"

# Create a new user
POST http://localhost:8080/api/users
Content-Type: application/json
[Options]
retry: 3
verbose: true
{
  "name": "John",
  "email": "john@example.com",
  "active": true,
  "age": 30
}

HTTP 201
[Asserts]
jsonpath "${'$'}.id" isInteger
jsonpath "${'$'}.name" == "John"
jsonpath "${'$'}.email" startsWith "john"
jsonpath "${'$'}.active" == true
jsonpath "${'$'}.age" >= 18

# Using variables
GET http://localhost:8080/api/users/{{user_id}}

HTTP 200
[Asserts]
jsonpath "${'$'}.id" == {{user_id}}

# Multiline body
POST http://localhost:8080/api/graphql
```graphql
{
  users {
    id
    name
  }
}
```

HTTP 200
""".trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = "Hurl"
}
