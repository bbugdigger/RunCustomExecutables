package com.github.bbugdigger.runcustomexecutables.runconfig

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.project.Project

/**
 * Factory for run configuration instances (Creates CustomExecutableRunConfiguration instances)
 *
 * https://plugins.jetbrains.com/docs/intellij/run-configurations.html#configurationfactory
 */
class CustomExecutableConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {

    override fun getId(): String = "CustomExecutableConfigurationFactory"

    /**
     * Creates a new template run configuration within the context of the specified project.
     *
     * @param project the project in which the run configuration will be used
     */
    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return CustomExecutableRunConfiguration(project, this, "Custom Executable")
    }

    override fun getOptionsClass(): Class<out BaseState> = CustomExecutableRunConfigurationOptions::class.java
}

