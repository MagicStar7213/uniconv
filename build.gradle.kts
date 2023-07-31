import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.8.22"
    id("org.jetbrains.compose") version "1.4.3"
}

group = "com.micromatic"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.material3:material3-desktop:1.4.3")
}

kotlin {
    jvmToolchain(18)
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
