@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.com.google.dagger.hilt.android)
}

android {
    namespace = "com.hanbikan.nook.core.testing"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":feature:phone")))
    implementation(project(mapOf("path" to ":feature:profile")))
    implementation(project(mapOf("path" to ":feature:todo")))
    implementation(project(mapOf("path" to ":feature:tutorial")))
    implementation(project(mapOf("path" to ":core:common")))
    implementation(project(mapOf("path" to ":core:database")))
    implementation(project(mapOf("path" to ":core:datastore")))
    implementation(project(mapOf("path" to ":core:designsystem")))
    implementation(project(mapOf("path" to ":core:domain")))
    implementation(project(mapOf("path" to ":core:ui")))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}