package com.adyen.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.Messages

class MyPluginAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project: Project? = event.project
        if (project == null) {
            Messages.showMessageDialog(project, "No project found", "Error", Messages.getErrorIcon())
            return
        }

        val editor: Editor? = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR)
        if (editor == null) {
            Messages.showMessageDialog(project, "No open editor", "Error", Messages.getErrorIcon())
            return
        }

        val selectionModel = editor.selectionModel
        val selectedText = selectionModel.selectedText

        if (selectedText.isNullOrBlank()) {
            Messages.showMessageDialog(project, "No text selected", "Error", Messages.getErrorIcon())
            return
        }

        // Get the current file and its extension
        val currentFile = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE)
        val fileExtension = currentFile?.extension ?: "txt"

        // Create a virtual file with the same extension
        val virtualFile: VirtualFile = LightVirtualFile("NewFile.$fileExtension", selectedText)

        // Get the file editor manager and open the file
        FileEditorManager.getInstance(project).openFile(virtualFile, true)
    }
}
