pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm") version "1.9.20"
        kotlin("plugin.serialization") version "1.9.20"
        id("org.jetbrains.compose") version "1.5.11"
    }
}

rootProject.name = "uniconv"

