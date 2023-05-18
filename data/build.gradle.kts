@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.kotlin.kapt)
    alias(libs.plugins.android.kotlin.ksp)
    alias(libs.plugins.android.hilt)
}

android {
    namespace = "com.moondroid.behealthy.data"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)

    // Kotlin
    implementation(libs.kotlin.reflect)

    // Gson
    implementation(libs.google.gson)

    //retrofit
    implementation(libs.bundles.squareup)

    // Room
    implementation(libs.bundles.room)
    implementation(libs.room.testing)
    //kapt -> ksp migration https://kotlinlang.org/docs/ksp-overview.html#supported-libraries
    ksp(libs.room.compiler)

    //hilt
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // clean architecture : project struct
    implementation((project(":domain")))
    implementation((project(":common")))
}

kapt {
    correctErrorTypes = true
}