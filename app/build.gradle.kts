import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.develop.properties)
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "API_ACCESS_TOKEN", value = "\"${developProperties.apiAccessToken}\"")
        buildConfigField("String", "DIPLOMA_PREFERENCES", "\"diploma_preferences\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)

    implementation(platform(libs.compose.bom))
    implementation(libs.navigation3.runtime)
    implementation(libs.navigation3.ui)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    testImplementation(libs.koin.test.junit4)
    implementation(libs.coil.compose)
    implementation(libs.compose.material3)
    implementation(libs.compose.activity)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.viewmodel.navigation3)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.manifest)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}
