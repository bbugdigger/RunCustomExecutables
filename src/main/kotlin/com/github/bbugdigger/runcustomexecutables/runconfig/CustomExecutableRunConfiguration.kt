package com.github.bbugdigger.runcustomexecutables.runconfig

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

/**
 * Run configuration for custom executables.
 * Stores settings for executable type, path, and arguments.
 */
class CustomExecutableRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String
) : RunConfigurationBase<CustomExecutableRunConfigurationOptions>(project, factory, name) {

    override fun getOptions(): CustomExecutableRunConfigurationOptions {
        return super.getOptions() as CustomExecutableRunConfigurationOptions
    }

    // Executable type: "rustc", "cargo", or "custom"
    var executableType: String
        get() = options.executableType
        set(value) { options.executableType = value }

    // Custom executable path
    var customExecutablePath: String
        get() = options.customExecutablePath
        set(value) { options.customExecutablePath = value }

    // Program arguments
    var programArguments: String
        get() = options.programArguments
        set(value) { options.programArguments = value }

    /**
     * Returns the actual executable path based on the configuration.
     */
    fun getResolvedExecutablePath(): String {
        return when (executableType) {
            "rustc" -> "rustc"  // Will be resolved from PATH
            "cargo" -> "cargo"  // Will be resolved from PATH
            else -> customExecutablePath
        }
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return CustomExecutableSettingsEditor(project)
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return CustomExecutableCommandLineState(this, environment)
    }

    override fun checkConfiguration() {
        if (executableType == "custom" && customExecutablePath.isBlank()) {
            throw RuntimeConfigurationError("Please specify an executable path")
        }
    }
}

