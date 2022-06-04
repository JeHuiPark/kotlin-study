plugins {
    // For build.gradle.kts (Kotlin DSL)
    kotlin("jvm") version "1.6.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    // https://mvnrepository.com/artifact/com.beust/klaxon
    implementation("com.beust:klaxon:5.6")

}
