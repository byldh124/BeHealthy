@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.kotlin.kapt)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.android.navigation.safeargs)
}

android {
    namespace = "com.moondroid.behealthy"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.moondroid.behealthy"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = false
            isDebuggable = false
            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.material)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.junit.espresso)

    // Glide
    implementation(libs.glide.android)
    kapt(libs.glide.compiler)

    // Gson
    implementation(libs.google.gson)

    // Kotlin
    implementation(libs.kotlin.reflect)

    // Lifecycle
    kapt(libs.androidx.lifecycle.compiler)
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.bundles.navigation)

    implementation((project(":common")))
    implementation((project(":data")))
    implementation((project(":domain")))
}

kapt {
    correctErrorTypes = true
}