package com.github.chadw.intellijhurl.template

import com.github.chadw.intellijhurl.language.HurlLanguage
import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType

class HurlLiveTemplateContext : TemplateContextType("Hurl") {

    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        val file = templateActionContext.file
        return file.language == HurlLanguage
    }
}
