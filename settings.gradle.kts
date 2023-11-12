pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm") version "1.9.10"
        kotlin("plugin.serialization") version "1.9.10"
        id("org.jetbrains.compose") version "1.5.3"
    }
}

rootProject.name = "uniconv"

