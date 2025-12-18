package com.github.bbugdigger.runcustomexecutables

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.RawCommandLineEditor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.RowLayout
import com.intellij.ui.dsl.builder.panel
import javax.swing.DefaultComboBoxModel
import javax.swing.JComponent

/**
 * Settings editor UI for the Custom Executable run configuration.
 * Follows IntelliJ UI Guidelines.
 */
class CustomExecutableSettingsEditor(private val project: Project) : com.intellij.openapi.options.SettingsEditor<CustomExecutableRunConfiguration>() {

    private val executableTypeComboBox = ComboBox(DefaultComboBoxModel(arrayOf(
        ExecutableChoice.RUSTC,
        ExecutableChoice.CARGO,
        ExecutableChoice.CUSTOM
    )))
    
    private val customExecutableField = TextFieldWithBrowseButton()
    private val programArgumentsEditor = RawCommandLineEditor()

    private lateinit var customExecutableRow: Row

    init {
        // Set up file chooser for custom executable
        customExecutableField.addBrowseFolderListener(
            "Select Executable",
            "Choose the executable file to run",
            project,
            FileChooserDescriptorFactory.createSingleFileDescriptor()
        )

        // Listen to combo box changes to show/hide custom path field
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

    override fun resetEditorFrom(config: CustomExecutableRunConfiguration) {
        // Set executable type
        val choice = when (config.executableType) {
            "rustc" -> ExecutableChoice.RUSTC
            "cargo" -> ExecutableChoice.CARGO
            else -> ExecutableChoice.CUSTOM
        }
        executableTypeComboBox.selectedItem = choice
        
        // Set custom executable path
        customExecutableField.text = config.customExecutablePath
        
        // Set program arguments
        programArgumentsEditor.text = config.programArguments

        updateCustomExecutableVisibility()
    }

    override fun applyEditorTo(config: CustomExecutableRunConfiguration) {
        // Apply executable type
        val choice = executableTypeComboBox.selectedItem as ExecutableChoice
        config.executableType = when (choice) {
            ExecutableChoice.RUSTC -> "rustc"
            ExecutableChoice.CARGO -> "cargo"
            ExecutableChoice.CUSTOM -> "custom"
        }
        
        // Apply custom executable path
        config.customExecutablePath = customExecutableField.text
        
        // Apply program arguments
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

    /**
     * Enum representing the available executable choices in the dropdown.
     */
    enum class ExecutableChoice(private val displayName: String) {
        RUSTC("Rust Compiler (rustc)"),
        CARGO("Cargo"),
        CUSTOM("Custom Executable");

        override fun toString(): String = displayName
    }
}

