plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    dependencies {
        implementation(project(":uniconv"))
        implementation(libs.androidx.core.ktx)
        implementation(libs.compose.activity)
        implementation(project.dependencies.platform(libs.compose.bom))
        implementation(libs.compose.ui)
        implementation(libs.compose.ui.graphics)
        implementation(libs.compose.material.icons)
        implementation(libs.compose.material3)
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.compose.resources)
    }
}

android {
    namespace = "io.magicstar.uniconv.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.magicstar.uniconv"
        minSdk = 26
        targetSdk = libs.versions.android.compileSdk.get().toInt()
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