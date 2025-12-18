package com.github.bbugdigger.runcustomexecutables

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

/**
 * To make your changes visible from the UI, implement a new run configuration.
 *
 * https://plugins.jetbrains.com/docs/intellij/run-configurations-tutorial.html#implement-a-runconfiguration
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

    var customExecutablePath: String
        get() = options.customExecutablePath
        set(value) { options.customExecutablePath = value }

    var programArguments: String
        get() = options.programArguments
        set(value) { options.programArguments = value }


    fun getResolvedExecutablePath(): String {
        return when (executableType) {
            "rustc" -> "rustc"
            "cargo" -> "cargo"
            else -> customExecutablePath
        }
    }

    /**
     * https://plugins.jetbrains.com/docs/intellij/run-configurations.html#settingseditor
     *
     * A run configuration may allow editing its general settings and settings specific to a program runner.
     * getConfigurationEditor() -> for editing run configuration settings
     */
    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return CustomExecutableSettingsEditor(project)
    }

    // Executor class describes a specific way of executing run profiles
    // ExecutionEnvironment object aggregates all the objects and settings required to execute the process
    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return CustomExecutableCommandLineState(this, environment)
    }

    override fun checkConfiguration() {
        if (executableType == "custom" && customExecutablePath.isBlank()) {
            throw RuntimeConfigurationError("Please specify an executable path")
        }
    }
}

