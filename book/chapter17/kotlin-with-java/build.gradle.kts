plugins {
    // For build.gradle.kts (Kotlin DSL)
    kotlin("jvm") version "1.6.20"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
}
