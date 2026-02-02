package com.github.chadw.intellijhurl.folding

import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class HurlFoldingBuilder : FoldingBuilderEx() {

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val descriptors = mutableListOf<FoldingDescriptor>()

        collectFoldRegions(root, descriptors, document)

        return descriptors.toTypedArray()
    }

    private fun collectFoldRegions(element: PsiElement, descriptors: MutableList<FoldingDescriptor>, document: Document) {
        val node = element.node ?: return
        val elementType = node.elementType

        // Fold entries (request + response blocks)
        if (elementType.toString() == "ENTRY") {
            val range = element.textRange
            if (range.length > 1 && spanMultipleLines(range, document)) {
                val firstLine = document.getLineNumber(range.startOffset)
                val lineEnd = document.getLineEndOffset(firstLine)
                if (lineEnd < range.endOffset) {
                    descriptors.add(FoldingDescriptor(node, TextRange(lineEnd, range.endOffset)))
                }
            }
        }

        // Fold sections
        if (HurlTokenTypes.SECTIONS.contains(elementType)) {
            val parent = element.parent
            if (parent != null) {
                val range = parent.textRange
                if (range.length > 1 && spanMultipleLines(range, document)) {
                    descriptors.add(FoldingDescriptor(parent.node, range))
                }
            }
        }

        // Fold multiline strings
        if (elementType == HurlTokenTypes.MULTILINE_STRING_OPEN) {
            val parent = element.parent
            if (parent != null) {
                val range = parent.textRange
                if (range.length > 6) {
                    descriptors.add(FoldingDescriptor(parent.node, range))
                }
            }
        }

        // Fold JSON bodies
        if (elementType == HurlTokenTypes.LBRACE) {
            val parent = element.parent
            if (parent != null && parent.node.elementType.toString() == "JSON_BODY") {
                val range = parent.textRange
                if (range.length > 2 && spanMultipleLines(range, document)) {
                    descriptors.add(FoldingDescriptor(parent.node, range))
                }
            }
        }

        for (child in element.children) {
            collectFoldRegions(child, descriptors, document)
        }
    }

    private fun spanMultipleLines(range: TextRange, document: Document): Boolean {
        return document.getLineNumber(range.startOffset) < document.getLineNumber(range.endOffset)
    }

    override fun getPlaceholderText(node: ASTNode): String {
        return when {
            node.elementType.toString() == "ENTRY" -> "..."
            node.elementType.toString() == "JSON_BODY" -> "{...}"
            node.elementType.toString() == "MULTILINE_STRING_BODY" -> "```...```"
            HurlTokenTypes.SECTIONS.contains(node.elementType) -> node.text.substringBefore("\n") + "..."
            else -> "..."
        }
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
