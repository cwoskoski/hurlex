package com.github.chadw.intellijhurl.run

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class HurlRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String
) : RunConfigurationBase<HurlRunConfigurationOptions>(project, factory, name) {

    override fun getOptions(): HurlRunConfigurationOptions {
        return super.getOptions() as HurlRunConfigurationOptions
    }

    var hurlFilePath: String?
        get() = options.hurlFilePath
        set(value) { options.hurlFilePath = value }

    var hurlOptions: String?
        get() = options.hurlOptions
        set(value) { options.hurlOptions = value }

    var workingDirectory: String?
        get() = options.workingDirectory
        set(value) { options.workingDirectory = value }

    var variablesFile: String?
        get() = options.variablesFile
        set(value) { options.variablesFile = value }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return HurlRunConfigurationEditor(project)
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return HurlCommandLineState(this, environment)
    }
}
