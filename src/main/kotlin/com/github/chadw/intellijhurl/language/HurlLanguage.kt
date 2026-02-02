package com.github.chadw.intellijhurl.language

import com.intellij.lang.Language

object HurlLanguage : Language("Hurl") {
    private fun readResolve(): Any = HurlLanguage

    override fun getDisplayName(): String = "Hurl"
}
