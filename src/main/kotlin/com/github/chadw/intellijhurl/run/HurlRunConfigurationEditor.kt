package com.github.chadw.intellijhurl.run

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class HurlRunConfigurationEditor(private val project: Project) : SettingsEditor<HurlRunConfiguration>() {
    private val hurlFileField = TextFieldWithBrowseButton()
    private val optionsField = JTextField()
    private val workingDirField = TextFieldWithBrowseButton()
    private val variablesFileField = TextFieldWithBrowseButton()
    private lateinit var panel: JPanel

    init {
        hurlFileField.addBrowseFolderListener(
            "Select Hurl File", "Choose the .hurl file to run",
            project, FileChooserDescriptorFactory.createSingleFileDescriptor("hurl")
        )
        workingDirField.addBrowseFolderListener(
            "Select Working Directory", "Choose the working directory",
            project, FileChooserDescriptorFactory.createSingleFolderDescriptor()
        )
        variablesFileField.addBrowseFolderListener(
            "Select Variables File", "Choose a variables file",
            project, FileChooserDescriptorFactory.createSingleFileDescriptor()
        )
    }

    override fun resetEditorFrom(config: HurlRunConfiguration) {
        hurlFileField.text = config.hurlFilePath ?: ""
        optionsField.text = config.hurlOptions ?: ""
        workingDirField.text = config.workingDirectory ?: ""
        variablesFileField.text = config.variablesFile ?: ""
    }

    override fun applyEditorTo(config: HurlRunConfiguration) {
        config.hurlFilePath = hurlFileField.text
        config.hurlOptions = optionsField.text
        config.workingDirectory = workingDirField.text
        config.variablesFile = variablesFileField.text
    }

    override fun createEditor(): JComponent {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Hurl file:", hurlFileField)
            .addLabeledComponent("Additional options:", optionsField)
            .addLabeledComponent("Working directory:", workingDirField)
            .addLabeledComponent("Variables file:", variablesFileField)
            .panel
        return panel
    }
}
