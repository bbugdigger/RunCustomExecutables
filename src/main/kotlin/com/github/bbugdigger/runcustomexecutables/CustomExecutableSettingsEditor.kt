package com.github.bbugdigger.runcustomexecutables

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.RawCommandLineEditor
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.RowLayout
import com.intellij.ui.dsl.builder.panel
import javax.swing.DefaultComboBoxModel
import javax.swing.JComponent

/**
 * Settings editor UI for the Custom Executable run configuration.
 *
 * https://plugins.jetbrains.com/docs/intellij/run-configurations.html#settingseditor
 *
 * A run configuration may allow editing its general settings and settings specific to a program runner.
 */
class CustomExecutableSettingsEditor(private val project: Project) : SettingsEditor<CustomExeRunConfiguration>() {

    private val executableTypeComboBox = ComboBox(DefaultComboBoxModel(arrayOf(
        ExecutableChoice.RUSTC,
        ExecutableChoice.CARGO,
        ExecutableChoice.CUSTOM
    )))
    
    private val customExecutableField = TextFieldWithBrowseButton()
    private val programArgumentsEditor = RawCommandLineEditor()

    private lateinit var customExecutableRow: Row

    init {
        customExecutableField.addBrowseFolderListener(
            "Select Executable",
            "Choose the executable file to run",
            project,
            FileChooserDescriptorFactory.createSingleFileDescriptor()
        )

        executableTypeComboBox.addActionListener {
            updateCustomExecutableVisibility()
        }
    }

    private fun updateCustomExecutableVisibility() {
        val selectedChoice = executableTypeComboBox.selectedItem as? ExecutableChoice
        val showCustomPath = selectedChoice == ExecutableChoice.CUSTOM
        customExecutableField.isEnabled = showCustomPath
        if (::customExecutableRow.isInitialized) {
            customExecutableRow.visible(showCustomPath)
        }
    }

    override fun resetEditorFrom(config: CustomExeRunConfiguration) {
        val choice = when (config.executableType) {
            "rustc" -> ExecutableChoice.RUSTC
            "cargo" -> ExecutableChoice.CARGO
            else -> ExecutableChoice.CUSTOM
        }
        executableTypeComboBox.selectedItem = choice
        
        customExecutableField.text = config.customExecutablePath
        
        programArgumentsEditor.text = config.programArguments

        updateCustomExecutableVisibility()
    }

    override fun applyEditorTo(config: CustomExeRunConfiguration) {
        val choice = executableTypeComboBox.selectedItem as ExecutableChoice
        config.executableType = when (choice) {
            ExecutableChoice.RUSTC -> "rustc"
            ExecutableChoice.CARGO -> "cargo"
            ExecutableChoice.CUSTOM -> "custom"
        }
        
        config.customExecutablePath = customExecutableField.text
        
        config.programArguments = programArgumentsEditor.text
    }

    override fun createEditor(): JComponent {
        return panel {
            row("Executable:") {
                cell(executableTypeComboBox)
                    .comment("Select a predefined executable or choose a custom file")
            }
            
            customExecutableRow = row("Executable path:") {
                cell(customExecutableField)
                    .align(AlignX.FILL)
                    .comment("Path to the executable file")
            }.layout(RowLayout.INDEPENDENT)
            
            row("Program arguments:") {
                cell(programArgumentsEditor)
                    .align(AlignX.FILL)
                    .comment("Arguments passed to the executable")
            }.layout(RowLayout.INDEPENDENT)
        }.also {
            updateCustomExecutableVisibility()
        }
    }

    enum class ExecutableChoice(private val displayName: String) {
        RUSTC("Rust Compiler (rustc)"),
        CARGO("Cargo"),
        CUSTOM("Custom Executable");

        override fun toString(): String = displayName
    }
}

