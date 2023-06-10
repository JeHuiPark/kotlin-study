plugins {
    kotlin("jvm") version "1.8.22"

    // https://github.com/jlleitschuh/ktlint-gradle
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

repositories {
    mavenCentral()
}

dependencies {
    addKotestDependencies()
    testImplementation("com.appmattus.fixture:fixture:1.2.0")
    testImplementation("io.mockk:mockk:1.12.5")
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    // optional

    enableExperimentalRules.set(true)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

fun DependencyHandlerScope.addKotestDependencies() {
    val kotestVersion = "5.6.2"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}
