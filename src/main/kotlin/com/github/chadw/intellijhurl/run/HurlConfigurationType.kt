package com.github.chadw.intellijhurl.run

import com.github.chadw.intellijhurl.language.HurlIcons
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import javax.swing.Icon

class HurlConfigurationType : ConfigurationType {
    override fun getDisplayName(): String = "Hurl"
    override fun getConfigurationTypeDescription(): String = "Run Hurl HTTP test file"
    override fun getIcon(): Icon = HurlIcons.RUN
    override fun getId(): String = "HurlRunConfiguration"
    override fun getConfigurationFactories(): Array<ConfigurationFactory> = arrayOf(HurlConfigurationFactory(this))
}
