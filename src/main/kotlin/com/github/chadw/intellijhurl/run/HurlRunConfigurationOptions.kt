package com.github.chadw.intellijhurl.run

import com.intellij.execution.configurations.RunConfigurationOptions
import com.intellij.openapi.components.StoredProperty

class HurlRunConfigurationOptions : RunConfigurationOptions() {
    private val myHurlFilePath: StoredProperty<String?> = string("").provideDelegate(this, "hurlFilePath")
    private val myHurlOptions: StoredProperty<String?> = string("").provideDelegate(this, "hurlOptions")
    private val myWorkingDirectory: StoredProperty<String?> = string("").provideDelegate(this, "workingDirectory")
    private val myVariablesFile: StoredProperty<String?> = string("").provideDelegate(this, "variablesFile")
    private val myTestMode: StoredProperty<Boolean> = property(false).provideDelegate(this, "testMode")
    private val myVerbose: StoredProperty<Boolean> = property(false).provideDelegate(this, "verbose")
    private val myVeryVerbose: StoredProperty<Boolean> = property(false).provideDelegate(this, "veryVerbose")
    private val myEnvironmentVariables: StoredProperty<String?> = string("").provideDelegate(this, "environmentVariables")
    private val myHurlExecutable: StoredProperty<String?> = string("").provideDelegate(this, "hurlExecutable")

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

    var testMode: Boolean
        get() = myTestMode.getValue(this)
        set(value) { myTestMode.setValue(this, value) }

    var verbose: Boolean
        get() = myVerbose.getValue(this)
        set(value) { myVerbose.setValue(this, value) }

    var veryVerbose: Boolean
        get() = myVeryVerbose.getValue(this)
        set(value) { myVeryVerbose.setValue(this, value) }

    var environmentVariables: String?
        get() = myEnvironmentVariables.getValue(this)
        set(value) { myEnvironmentVariables.setValue(this, value) }

    var hurlExecutable: String?
        get() = myHurlExecutable.getValue(this)
        set(value) { myHurlExecutable.setValue(this, value) }
}
