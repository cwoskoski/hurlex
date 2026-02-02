package com.github.chadw.intellijhurl.run

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.components.BaseState
import com.intellij.openapi.project.Project

class HurlConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {
    override fun getId(): String = "HurlConfigurationFactory"
    override fun getName(): String = "Hurl"

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return HurlRunConfiguration(project, this, "Hurl")
    }

    override fun getOptionsClass(): Class<out BaseState> = HurlRunConfigurationOptions::class.java
}
