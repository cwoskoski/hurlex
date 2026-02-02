package com.github.chadw.intellijhurl.formatting

import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock

class HurlBlock(
    node: ASTNode,
    wrap: Wrap?,
    private val indent: Indent,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, null) {

    override fun buildChildren(): List<Block> {
        val blocks = mutableListOf<Block>()
        var child = myNode.firstChildNode

        while (child != null) {
            val type = child.elementType
            if (type != TokenType.WHITE_SPACE && type != HurlTokenTypes.WHITE_SPACE) {
                val childIndent = calculateChildIndent(child)
                blocks.add(HurlBlock(child, null, childIndent, spacingBuilder))
            }
            child = child.treeNext
        }

        return blocks
    }

    private fun calculateChildIndent(child: ASTNode): Indent {
        val parentType = myNode.elementType.toString()
        val childType = child.elementType

        // Indent contents inside sections (key-value pairs, assertions, captures)
        if (parentType.endsWith("_SECTION") && !HurlTokenTypes.SECTIONS.contains(childType)) {
            if (childType != HurlTokenTypes.NEWLINE) {
                return Indent.getNormalIndent()
            }
        }

        return Indent.getNoneIndent()
    }

    override fun getIndent(): Indent = indent

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean = myNode.firstChildNode == null
}
