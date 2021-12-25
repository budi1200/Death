val ktormVersion = "3.4.1"
val kotlinVersion = "1.6.10"
val configurateVersion = "4.1.2"
val jdbcVersion = "3.36.0.2"
val jodaTimeVersion = "2.10.13"

plugins {
    id("java")
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

group = "si.budimir"
version = "1.1"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.ktorm:ktorm-core:${ktormVersion}")
    implementation("org.ktorm:ktorm-support-sqlite:${ktormVersion}")

    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    implementation("joda-time:joda-time:$jodaTimeVersion")

    implementation("org.xerial:sqlite-jdbc:$jdbcVersion")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    implementation("org.spongepowered:configurate-hocon:$configurateVersion")
    implementation("org.spongepowered:configurate-extra-kotlin:$configurateVersion")
}

tasks.processResources {
    expand(
        "version" to project.version,
        "kotlinVersion" to kotlinVersion,
        "configurateVersion" to configurateVersion,
    )
}

tasks.shadowJar {
    relocate("org.ktorm", "si.budimir.death.libs.org.ktorm")
    relocate("org.sqlite", "si.budimir.death.libs.sqlite")
    relocate("org.spongepowered", "si.budimir.death.libs.org.spongepowered")

    mergeServiceFiles()
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