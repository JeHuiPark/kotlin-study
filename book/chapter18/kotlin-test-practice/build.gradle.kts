plugins {
    // For build.gradle.kts (Kotlin DSL)
    kotlin("jvm") version "1.6.20"
    application
    jacoco
}

repositories {
    mavenCentral()
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("com.beust:klaxon:5.6")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("io.mockk:mockk:1.12.4")
}

tasks {
    getByName<JacocoReport>("jacocoTestReport") {
        afterEvaluate {
            classDirectories.setFrom(files(classDirectories.files.map {
                fileTree(it) { exclude("**/ui/**") }
            }))
        }
    }
}

jacoco {
    toolVersion = "0.8.3"
}

application {
    mainClass.set("com.agiledeveloper.ui.AirportAppKt")
}

defaultTasks("clean", "test", "jacocoTestReport")
