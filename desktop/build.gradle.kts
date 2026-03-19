import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
}

group = "io.magicstar"
version = libs.versions.uniconv.get()

kotlin {
    jvmToolchain(21)
    compilerOptions.languageVersion = KotlinVersion.KOTLIN_2_3

    jvm()
    sourceSets {
        jvmMain {
            dependencies {
                implementation(project(":uniconv"))
                implementation(compose.desktop.currentOs)
                implementation(libs.material3)
                implementation(libs.compose.material.icons.desktop)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.compose.resources)
            }
        }
        jvmTest {
            dependencies {
                implementation(project(":uniconv"))
                implementation(kotlin("test"))
                implementation(libs.systems.common)
                implementation(libs.junit.jupiter.params)
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

compose.desktop {
    application {
        mainClass = "io.magicstar.uniconv.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "uniconv"
            packageVersion = project.version.toString()
            description = "Simple unit converter refactored. Supports the most common magnitudes and units"
            vendor = "MagicStar7213"
            windows {
                iconFile.set(project.file("uniconv.ico"))
                dirChooser = true
                perUserInstall = true
                menu = true
            }
            macOS {
                iconFile.set(project.file("uniconv.icns"))
            }
            linux {
                iconFile.set(project.file("uniconv.png"))
            }
        }
        buildTypes.release.proguard.version.set("7.7.0")
    }
}