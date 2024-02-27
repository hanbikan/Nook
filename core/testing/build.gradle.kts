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

    api("androidx.test:core:1.5.0")
    api("androidx.test:runner:1.5.2")
    api("androidx.test:rules:1.5.0")
    api("androidx.test.ext:junit:1.1.5")
    api("androidx.test.ext:truth:1.5.0")
    api("androidx.test.espresso:espresso-core:3.5.1")
    api("androidx.test.espresso:espresso-contrib:3.5.1")
    api("androidx.test.espresso:espresso-intents:3.5.1")
    api("androidx.test.espresso:espresso-accessibility:3.5.1")
    api("androidx.test.espresso:espresso-web:3.5.1")
    api("androidx.test.espresso.idling:idling-concurrent:3.5.1")
    api("androidx.test.espresso:espresso-idling-resource:3.5.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
}