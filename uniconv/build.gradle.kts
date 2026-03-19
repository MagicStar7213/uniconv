import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.kmp)
}

group = "io.magicstar"
version = libs.versions.uniconv.get()

kotlin {
    jvmToolchain(21)
    compilerOptions.languageVersion = KotlinVersion.KOTLIN_2_3

    jvm()
    android {
        namespace = "io.magicstar.uniconv"
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }

        androidResources {
            enable = true
        }
    }

    sourceSets {
        applyDefaultHierarchyTemplate()
        commonMain {
            dependencies {
                implementation(libs.compose.resources)
                implementation(libs.runtime)
            }
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}

buildscript {
    dependencies {
        classpath(libs.kotlin.gradle)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
