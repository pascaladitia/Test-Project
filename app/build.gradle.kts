plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.pascal.testproject"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.pascal.testproject"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // UI
    implementation(libs.constraintlayout.compose)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.material.icons.core)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.pager)
    implementation(libs.feather.icons)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kotlinx.serialization.json)

    // Koin
    implementation(libs.koin)
    implementation(libs.koin.annotations)

    // Paging
    implementation(libs.paging.common.ktx)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)

    // Navigation
    implementation(libs.navigation)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.runtime.android)
    ksp(libs.room.compiler)

    // Chucker
    releaseImplementation(libs.chuckerReleaseNoOp)
    debugImplementation(libs.chuckerDebug)

    // Exo
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    // Other
    implementation(libs.datastore.preferences)
    implementation(libs.itext7.core)
}