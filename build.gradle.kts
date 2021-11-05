val exposedVersion = "0.34.1"
val kotlinVersion = "1.5.31"

plugins {
    kotlin("jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "si.budimir"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.exposed:exposed-core:$exposedVersion")
    compileOnly("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    compileOnly("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

tasks.processResources {
    expand("version" to project.version)
    expand("kotlinVersion" to kotlinVersion)
    expand("exposedVersion" to exposedVersion)
}

tasks.shadowJar {
    // This makes it shadow only stuff with "implementation"
    project.configurations.implementation.get().isCanBeResolved = true
    configurations = mutableListOf(project.configurations.implementation.get())

    minimize {}
}

task("buildAndPush") {
    dependsOn("shadowJar")

    doLast {
        copy {
            from("build/libs/Death-" + project.version + "-all.jar")
            into("../00-server/plugins/")
        }
    }
}