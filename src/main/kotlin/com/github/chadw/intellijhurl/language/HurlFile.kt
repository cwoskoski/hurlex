package com.github.chadw.intellijhurl.language

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class HurlFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, HurlLanguage) {
    override fun getFileType(): FileType = HurlFileType
    override fun toString(): String = "Hurl File"
}
