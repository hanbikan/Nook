@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(mapOf("path" to ":core:domain")))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    api("junit:junit:4.13.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
}