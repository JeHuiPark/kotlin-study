plugins {
    // For build.gradle.kts (Kotlin DSL)
    kotlin("jvm") version "1.6.20"
}

repositories {
    mavenCentral()
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
}
