package com.github.chadw.intellijhurl.language

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object HurlFileType : LanguageFileType(HurlLanguage) {
    override fun getName(): String = "Hurl"
    override fun getDescription(): String = "Hurl HTTP testing file"
    override fun getDefaultExtension(): String = "hurl"
    override fun getIcon(): Icon = HurlIcons.FILE
}
