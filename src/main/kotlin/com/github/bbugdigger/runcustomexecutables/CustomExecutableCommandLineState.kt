package com.github.bbugdigger.runcustomexecutables

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.util.execution.ParametersListUtil

/**
 * Starts the execution of the custom executable.
 *
 * https://plugins.jetbrains.com/docs/intellij/execution.html#run-profile-state
 */
class CustomExecutableCommandLineState(
    private val configuration: CustomExeRunConfiguration,
    environment: ExecutionEnvironment
) : CommandLineState(environment) {

    override fun startProcess(): ProcessHandler {
        val executablePath = configuration.getResolvedExecutablePath()
        
        if (executablePath.isBlank()) {
            throw ExecutionException("Executable path is not specified")
        }

        val commandLine = GeneralCommandLine()
        commandLine.exePath = executablePath
        
        val arguments = configuration.programArguments
        if (arguments.isNotBlank()) {
            val parsedArgs = ParametersListUtil.parse(arguments)
            commandLine.addParameters(parsedArgs)
        }

        //https://plugins.jetbrains.com/docs/intellij/execution.html#process-handler
        //now this is for monitoring the execution of a process and capturing its output
        val processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine)
        ProcessTerminatedListener.attach(processHandler)
        
        return processHandler
    }
}

