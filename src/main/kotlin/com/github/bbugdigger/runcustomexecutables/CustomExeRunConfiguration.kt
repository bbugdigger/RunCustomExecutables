package com.github.bbugdigger.runcustomexecutables

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class CustomExeRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String
) : RunConfigurationBase<CustomExeRunConfigurationOptions>(project, factory, name) {

    override fun getOptions(): CustomExeRunConfigurationOptions {
        return super.getOptions() as CustomExeRunConfigurationOptions
    }

    // Executable type: "rustc", "cargo", or "custom"
    var executableType: String
        get() = options.executableType
        set(value) { options.executableType = value }

    var customExecutablePath: String
        get() = options.customExecutablePath
        set(value) { options.customExecutablePath = value }

    var programArguments: String
        get() = options.programArguments
        set(value) { options.programArguments = value }


    fun getCustomExecutablePath(): String {
        return when (executableType) {
            "rustc" -> "rustc"
            "cargo" -> "cargo"
            else -> customExecutablePath
        }
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return CustomExeSettingsEditor(project)
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return CustomExeCommandLineState(this, environment)
    }

    override fun checkConfiguration() {
        if (executableType == "custom" && customExecutablePath.isBlank()) {
            throw RuntimeConfigurationError("Please specify an executable path")
        }
    }
}
