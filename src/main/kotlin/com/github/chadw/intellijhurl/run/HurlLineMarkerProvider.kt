package com.github.chadw.intellijhurl.run

import com.github.chadw.intellijhurl.language.HurlIcons
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.psi.PsiElement

class HurlLineMarkerProvider : RunLineMarkerContributor() {

    override fun getInfo(element: PsiElement): Info? {
        val elementType = element.node?.elementType ?: return null

        // Only show run icon on HTTP method tokens
        if (!HurlTokenTypes.METHODS.contains(elementType)) return null

        // Make sure this is at the start of a request (not nested somewhere unexpected)
        val actions = ExecutorAction.getActions(0)

        return Info(
            HurlIcons.RUN,
            { "Run ${element.text} request" },
            *actions
        )
    }
}
