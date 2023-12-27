import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
}

group = "com.micromatic"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.material3:material3-desktop:1.5.11")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.5.11")
}

kotlin {
    if (DefaultNativePlatform.getCurrentOperatingSystem().isWindows) jvmToolchain(18)
    else jvmToolchain(20)
}

compose.desktop {
    application {
        mainClass = "io.magicstar.uniconv.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "uniconv"
            packageVersion = "1.0.0"
        }
    }
}
