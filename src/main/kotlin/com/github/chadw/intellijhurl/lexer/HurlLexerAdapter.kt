package com.github.chadw.intellijhurl.lexer

import com.intellij.lexer.FlexAdapter

class HurlLexerAdapter : FlexAdapter(HurlLexer(null))
