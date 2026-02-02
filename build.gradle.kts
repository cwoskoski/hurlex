import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

repositories {
    mavenCentral()
}

intellij {
    pluginName.set(providers.gradleProperty("pluginName"))
    version.set(providers.gradleProperty("platformVersion"))
    type.set(providers.gradleProperty("platformType"))
}

sourceSets {
    main {
        java.srcDirs("src/main/gen")
    }
}

tasks.named<GenerateLexerTask>("generateLexer") {
    sourceFile.set(file("src/main/kotlin/com/github/chadw/intellijhurl/lexer/Hurl.flex"))
    targetOutputDir.set(file("src/main/gen/com/github/chadw/intellijhurl/lexer"))
    purgeOldFiles.set(true)
}

tasks.named<GenerateParserTask>("generateParser") {
    sourceFile.set(file("src/main/kotlin/com/github/chadw/intellijhurl/parser/Hurl.bnf"))
    targetRootOutputDir.set(file("src/main/gen"))
    pathToParser.set("com/github/chadw/intellijhurl/parser/HurlParser.java")
    pathToPsiRoot.set("com/github/chadw/intellijhurl/psi")
    purgeOldFiles.set(true)
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        dependsOn("generateLexer", "generateParser")
        kotlinOptions.jvmTarget = providers.gradleProperty("javaVersion").get()
    }

    withType<JavaCompile> {
        dependsOn("generateLexer", "generateParser")
        sourceCompatibility = providers.gradleProperty("javaVersion").get()
        targetCompatibility = providers.gradleProperty("javaVersion").get()
    }

    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("251.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    test {
        useJUnitPlatform()
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
