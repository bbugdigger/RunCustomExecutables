package com.github.bbugdigger.runcustomexecutables

import com.intellij.execution.configurations.RunConfigurationOptions

/**
 * Stores the run configuration options/settings.
 * Uses property delegation for automatic persistence.
 */
class CustomExecutableRunConfigurationOptions : RunConfigurationOptions() {
    
    /**
     * The type of executable selection:
     * - "rustc" for Rust Compiler
     * - "cargo" for Cargo
     * - "custom" for custom file path
     */
    private val _executableType = string("custom").provideDelegate(this, "executableType")
    var executableType: String
        get() = _executableType.getValue(this) ?: "custom"
        set(value) { _executableType.setValue(this, value) }

    /**
     * Custom executable path (used when executableType is "custom")
     */
    private val _customExecutablePath = string("").provideDelegate(this, "customExecutablePath")
    var customExecutablePath: String
        get() = _customExecutablePath.getValue(this) ?: ""
        set(value) { _customExecutablePath.setValue(this, value) }

    /**
     * Arguments to pass to the executable
     */
    private val _programArguments = string("").provideDelegate(this, "programArguments")
    var programArguments: String
        get() = _programArguments.getValue(this) ?: ""
        set(value) { _programArguments.setValue(this, value) }
}

