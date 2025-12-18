package com.github.bbugdigger.runcustomexecutables

import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.SimpleConfigurationType
import com.intellij.icons.AllIcons
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NotNullLazyValue

/**
 * This is the entry point for the run configuration:
 * https://plugins.jetbrains.com/docs/intellij/run-configurations.html#configurationtype
 *
 * It appears in the "Add New Run Configuration" dialog in IntelliJ.
 * 
 * Uses SimpleConfigurationType since we only have a single configuration factory.
 * SimpleConfigurationType acts as both the configuration type AND the factory,
 * eliminating the need for a separate factory class.
 */
class CustomExecutableConfigurationType : SimpleConfigurationType(
    "CustomExecutableRunConfiguration",
    "Custom Executable",
    "Run a custom executable",
    NotNullLazyValue.createValue { AllIcons.RunConfigurations.Application }
) {

    /**
     * Creates a new template run configuration within the context of the specified project.
     * This method is called once for each project to create the run configuration template.
     *
     * @param project the project in which the run configuration will be used
     * @return a new run configuration instance
     */
    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return CustomExecutableRunConfiguration(project, this, "Custom Executable")
    }

    /**
     * Returns the options class used to store the run configuration settings.
     *
     * @return the options class
     */
    override fun getOptionsClass(): Class<out BaseState> {
        return CustomExecutableRunConfigurationOptions::class.java
    }
}
