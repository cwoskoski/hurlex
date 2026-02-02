package com.github.chadw.intellijhurl

import com.github.chadw.intellijhurl.language.HurlFile
import com.github.chadw.intellijhurl.language.HurlFileType
import com.github.chadw.intellijhurl.parser.HurlParserDefinition
import com.intellij.testFramework.ParsingTestCase

class HurlParsingTest : ParsingTestCase("", "hurl", HurlParserDefinition()) {

    override fun getTestDataPath(): String = "src/test/resources"

    fun testSampleFile() {
        doTest(true)
    }

    fun testSimpleGet() {
        doTest(true)
    }

    fun testPostWithJson() {
        doTest(true)
    }

    fun testMultipleEntries() {
        doTest(true)
    }

    fun testCapturesAndVariables() {
        doTest(true)
    }

    fun testAllSections() {
        doTest(true)
    }

    override fun skipSpaces(): Boolean = false
    override fun includeRanges(): Boolean = true
}
