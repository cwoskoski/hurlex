package com.github.chadw.intellijhurl.structure

import com.github.chadw.intellijhurl.language.HurlFile
import com.github.chadw.intellijhurl.language.HurlIcons
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

class HurlStructureViewElement(private val element: NavigatablePsiElement) :
    StructureViewTreeElement, SortableTreeElement {

    override fun getValue(): Any = element

    override fun navigate(requestFocus: Boolean) = element.navigate(requestFocus)

    override fun canNavigate(): Boolean = element.canNavigate()

    override fun canNavigateToSource(): Boolean = element.canNavigateToSource()

    override fun getAlphaSortKey(): String = presentation.presentableText ?: ""

    override fun getPresentation(): ItemPresentation {
        if (element is HurlFile) {
            return PresentationData(element.name, null, HurlIcons.FILE, null)
        }

        val node = element.node
        val elementType = node?.elementType?.toString()

        if (elementType == "ENTRY" || elementType == "REQUEST") {
            val text = getEntryLabel(element)
            return PresentationData(text, null, HurlIcons.RUN, null)
        }

        if (elementType == "RESPONSE") {
            val text = getResponseLabel(element)
            return PresentationData(text, null, null, null)
        }

        // Section nodes
        if (HurlTokenTypes.SECTIONS.contains(node?.elementType)) {
            return PresentationData(node?.text ?: "Section", null, null, null)
        }

        val parent = element.parent?.node
        if (parent != null && parent.elementType.toString().endsWith("_SECTION")) {
            val sectionNode = parent.findChildByType(HurlTokenTypes.SECTIONS)
            val sectionName = sectionNode?.text ?: parent.elementType.toString()
            return PresentationData(sectionName, null, null, null)
        }

        return PresentationData(element.text?.take(40) ?: "", null, null, null)
    }

    override fun getChildren(): Array<TreeElement> {
        if (element is HurlFile) {
            return element.children
                .filter { it.node?.elementType?.toString() == "ENTRY" }
                .map { HurlStructureViewElement(it as NavigatablePsiElement) }
                .toTypedArray()
        }

        val elementType = element.node?.elementType?.toString()
        if (elementType == "ENTRY") {
            val children = mutableListOf<TreeElement>()
            for (child in element.children) {
                val childType = child.node?.elementType?.toString()
                if (childType == "REQUEST" || childType == "RESPONSE") {
                    children.add(HurlStructureViewElement(child as NavigatablePsiElement))
                }
            }
            return children.toTypedArray()
        }

        if (elementType == "REQUEST" || elementType == "RESPONSE") {
            return element.children
                .filter { it.node?.elementType?.toString()?.endsWith("_SECTION") == true }
                .map { HurlStructureViewElement(it as NavigatablePsiElement) }
                .toTypedArray()
        }

        return emptyArray()
    }

    private fun getEntryLabel(element: PsiElement): String {
        val text = element.text ?: return "Request"
        val firstLine = text.lineSequence().firstOrNull()?.trim() ?: return "Request"
        return firstLine.take(60)
    }

    private fun getResponseLabel(element: PsiElement): String {
        val text = element.text ?: return "Response"
        val firstLine = text.lineSequence().firstOrNull()?.trim() ?: return "Response"
        return firstLine.take(60)
    }
}
