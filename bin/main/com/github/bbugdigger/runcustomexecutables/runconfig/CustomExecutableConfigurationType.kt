package com.github.bbugdigger.runcustomexecutables.runconfig

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.icons.AllIcons
import javax.swing.Icon

/**
 * This is the entry point for the run configuration:
 * https://plugins.jetbrains.com/docs/intellij/run-configurations.html#configurationtype
 *
 * It appears in the "Add New Run Configuration" dialog in IntelliJ
 */
class CustomExecutableConfigurationType : ConfigurationType {

    /**
     * Returns the display name of the configuration type. This is used, for example, to represent the configuration type in the run
     * configurations tree, and also as the name of the action used to create the configuration.
     *
     * @return the display name of the configuration type.
     */
    override fun getDisplayName(): String = "Custom Executable"

    /**
     * Returns the description of the configuration type. You may return the same text as the display name of the configuration type.
     *
     * @return the description of the configuration type.
     */
    override fun getConfigurationTypeDescription(): String = "Run a custom executable"

    /**
     * Returns the 16x16 icon used to represent the configuration type.
     *
     * @return the icon
     */
    override fun getIcon(): Icon = AllIcons.RunConfigurations.Application

    /**
     * The ID of the configuration type. Should be camel-cased without dashes, underscores, spaces and quotation marks.
     * The ID is used to store run configuration settings in a project or workspace file and
     * must not change between plugin versions.
     */
    override fun getId(): String = "CustomExecutableRunConfiguration"

    /**
     * Returns the configuration factories used by this configuration type. Normally, each configuration type provides just a single factory.
     * You can return multiple factories if your configurations can be created in multiple variants (for example, local and remote for an
     * application server).
     *
     * @return the run configuration factories.
     */
    override fun getConfigurationFactories(): Array<ConfigurationFactory> = arrayOf(CustomExecutableConfigurationFactory(this))
}
