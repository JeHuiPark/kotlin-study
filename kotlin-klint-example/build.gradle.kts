plugins {
    kotlin("jvm") version "1.6.20"

    // https://github.com/jlleitschuh/ktlint-gradle
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

repositories {
    mavenCentral()
}

dependencies {
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    // optional

    enableExperimentalRules.set(true)
}
