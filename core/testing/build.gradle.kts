@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
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

    api(libs.junit)
    api(libs.androidx.test.ext.junit)
    api(libs.espresso.core)
    api(libs.junit)
    api(libs.androidx.test.ext.junit)
    api(libs.espresso.core)
    api(platform(libs.compose.bom))
    api(libs.ui.test.junit4)
    api(libs.kotlinx.coroutines.test)

    api(libs.mockito.inline)
    api(libs.mockito.kotlin)
}