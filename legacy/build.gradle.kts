import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.android.navigation.safeargs)
    alias(libs.plugins.google.service)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.android.kotlin.kapt)
}

val properties = gradleLocalProperties(rootDir)

android {
    namespace = "com.moondroid.legacy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moondroid.legacy"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val kakaoScheme: String = properties.getProperty("kakao.native.app.key.scheme")
        manifestPlaceholders["kakaoScheme"] = kakaoScheme

        val kakaoAppKey: String = properties.getProperty("kakao.native.app.key")
        resValue("string", "kakao_app_key", kakaoAppKey)
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

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(libs.bundles.kotlin)
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

    // Lifecycle
    kapt(libs.androidx.lifecycle.compiler)
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.bundles.navigation)

    implementation(libs.bundles.firebase)

    implementation(libs.google.sign)

    implementation(libs.bundles.kakao)

    implementation(libs.lottie.animation)

    implementation((project(":common")))
    implementation((project(":data")))
    implementation((project(":domain")))
}

kapt {
    correctErrorTypes = true
}