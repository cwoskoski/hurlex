package com.github.chadw.intellijhurl.language

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object HurlIcons {
    @JvmField val FILE: Icon = IconLoader.getIcon("/icons/hurl.svg", HurlIcons::class.java)
    @JvmField val RUN: Icon = IconLoader.getIcon("/icons/hurl-run.svg", HurlIcons::class.java)
}
