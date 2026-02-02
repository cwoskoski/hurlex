package com.github.chadw.intellijhurl.navigation

import com.github.chadw.intellijhurl.language.HurlFileType
import com.github.chadw.intellijhurl.lexer.HurlTokenTypes
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiRecursiveElementVisitor
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope

class HurlChooseByNameContributor : ChooseByNameContributor {

    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        val names = mutableSetOf<String>()
        val scope = if (includeNonProjectItems) GlobalSearchScope.allScope(project)
                    else GlobalSearchScope.projectScope(project)

        FileTypeIndex.getFiles(HurlFileType, scope).forEach { virtualFile ->
            val psiFile = PsiManager.getInstance(project).findFile(virtualFile) ?: return@forEach
            psiFile.accept(object : PsiRecursiveElementVisitor() {
                override fun visitElement(element: PsiElement) {
                    if (element.node?.elementType == HurlTokenTypes.KEY_STRING) {
                        val parent = element.parent?.parent
                        if (parent?.node?.elementType?.toString()?.contains("CAPTURE", ignoreCase = true) == true) {
                            names.add(element.text)
                        }
                    }
                    super.visitElement(element)
                }
            })
        }

        return names.toTypedArray()
    }

    override fun getItemsByName(
        name: String,
        pattern: String,
        project: Project,
        includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        val items = mutableListOf<NavigationItem>()
        val scope = if (includeNonProjectItems) GlobalSearchScope.allScope(project)
                    else GlobalSearchScope.projectScope(project)

        FileTypeIndex.getFiles(HurlFileType, scope).forEach { virtualFile ->
            val psiFile = PsiManager.getInstance(project).findFile(virtualFile) ?: return@forEach
            psiFile.accept(object : PsiRecursiveElementVisitor() {
                override fun visitElement(element: PsiElement) {
                    if (element.node?.elementType == HurlTokenTypes.KEY_STRING && element.text == name) {
                        val parent = element.parent?.parent
                        if (parent?.node?.elementType?.toString()?.contains("CAPTURE", ignoreCase = true) == true) {
                            if (element is NavigationItem) {
                                items.add(element)
                            }
                        }
                    }
                    super.visitElement(element)
                }
            })
        }

        return items.toTypedArray()
    }
}
