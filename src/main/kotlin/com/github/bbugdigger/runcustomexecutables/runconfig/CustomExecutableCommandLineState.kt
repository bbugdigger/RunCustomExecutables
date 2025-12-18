package com.github.bbugdigger.runcustomexecutables.runconfig

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.util.execution.ParametersListUtil
import java.io.File

/**
 * Handles the execution of the custom executable.
 * Creates the process and manages its lifecycle.
 */
class CustomExecutableCommandLineState(
    private val configuration: CustomExecutableRunConfiguration,
    environment: ExecutionEnvironment
) : CommandLineState(environment) {

    override fun startProcess(): ProcessHandler {
        val executablePath = configuration.getResolvedExecutablePath()
        
        if (executablePath.isBlank()) {
            throw ExecutionException("Executable path is not specified")
        }

        val commandLine = GeneralCommandLine()
        
        // Set the executable
        commandLine.exePath = executablePath
        
        // Parse and add program arguments
        val arguments = configuration.programArguments
        if (arguments.isNotBlank()) {
            val parsedArgs = ParametersListUtil.parse(arguments)
            commandLine.addParameters(parsedArgs)
        }
        
        // Create and configure process handler
        val processHandler = ProcessHandlerFactory.getInstance()
            .createColoredProcessHandler(commandLine)
        
        // Attach listener to handle process termination
        ProcessTerminatedListener.attach(processHandler)
        
        return processHandler
    }
}

