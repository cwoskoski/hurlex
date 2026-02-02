package com.github.chadw.intellijhurl.run

import com.intellij.execution.configurations.RunConfigurationOptions
import com.intellij.openapi.components.StoredProperty

class HurlRunConfigurationOptions : RunConfigurationOptions() {
    private val myHurlFilePath: StoredProperty<String?> = string("").provideDelegate(this, "hurlFilePath")
    private val myHurlOptions: StoredProperty<String?> = string("").provideDelegate(this, "hurlOptions")
    private val myWorkingDirectory: StoredProperty<String?> = string("").provideDelegate(this, "workingDirectory")
    private val myVariablesFile: StoredProperty<String?> = string("").provideDelegate(this, "variablesFile")

    var hurlFilePath: String?
        get() = myHurlFilePath.getValue(this)
        set(value) { myHurlFilePath.setValue(this, value) }

    var hurlOptions: String?
        get() = myHurlOptions.getValue(this)
        set(value) { myHurlOptions.setValue(this, value) }

    var workingDirectory: String?
        get() = myWorkingDirectory.getValue(this)
        set(value) { myWorkingDirectory.setValue(this, value) }

    var variablesFile: String?
        get() = myVariablesFile.getValue(this)
        set(value) { myVariablesFile.setValue(this, value) }
}
