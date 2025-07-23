import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("com.android.application")
}

group = "io.magicstar"
version = "2.3.0"

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            languageVersion = KotlinVersion.KOTLIN_2_2
        }
    }

    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            languageVersion = KotlinVersion.KOTLIN_2_2
        }
    }

    sourceSets {
        applyDefaultHierarchyTemplate()
        commonMain {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.runtime)
            }
        }
        androidMain {
            dependencies {
                implementation("androidx.core:core-ktx:1.16.0")
                implementation("androidx.activity:activity-compose:1.10.1")
                implementation(project.dependencies.platform("androidx.compose:compose-bom:2025.06.01"))
                implementation("androidx.compose.ui:ui")
                implementation("androidx.compose.ui:ui-graphics")
                implementation("androidx.compose.material3:material3")
                implementation("androidx.compose.material:material-icons-extended")
                implementation("androidx.datastore:datastore-preferences:1.1.7")
            }
        }
        jvmMain {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
                implementation(compose.materialIconsExtended)
            }
        }
        jvmTest {
            dependencies {
                implementation(kotlin("test"))
                implementation("systems.uom:systems-common:2.1")
                implementation("org.junit.jupiter:junit-jupiter-params:5.13.2")
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

android {
    namespace = "io.magicstar.uniconv"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.magicstar.uniconv"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = project.version.toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

compose.desktop {
    application {
        mainClass = "io.magicstar.uniconv.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Uniconv"
            packageVersion = project.version.toString()
            description = "Simple unit converter refactored. Supports the most common magnitudes and units"
            vendor = "MagicStar7213"
            windows {
                packageVersion = "${project.version}"
            }
            macOS {
                iconFile.set(project.file("uniconv.icns"))
            }
            linux {
                iconFile.set(project.file("uniconv.png"))
            }
        }
        buildTypes.release.proguard.version.set("7.5.0")
    }
}
