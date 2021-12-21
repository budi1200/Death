val exposedVersion = "0.36.2"
val kotlinVersion = "1.6.10"
val configurateVersion = "4.1.2"
val jdbcVersion = "3.36.0.2"
val jodaTimeVersion = "2.10.13"

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.1"
//    id("net.kyori.blossom") version "1.3.0"
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
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")

    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    implementation("joda-time:joda-time:$jodaTimeVersion")

    // Needs to be shaded
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.xerial:sqlite-jdbc:$jdbcVersion")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    implementation("org.spongepowered:configurate-hocon:$configurateVersion")
    implementation("org.spongepowered:configurate-extra-kotlin:$configurateVersion")
}

tasks.processResources {
    expand(
        "version" to project.version,
        "kotlinVersion" to kotlinVersion,
        "exposedVersion" to exposedVersion,
        "configurateVersion" to configurateVersion,
    )
}

//blossom {
//    val file = "src/main/kotlin/si/budimir/death/enums/Constants.kt"
//    mapOf(
//        "PLUGIN_NAME" to rootProject.name,
//        "PLUGIN_VERSION" to project.version
//    ).forEach { (k, v) ->
//        replaceToken("{$k}", v, file)
//    }
//}

tasks.shadowJar {
    // This makes it shadow only stuff with "implementation"
    project.configurations.implementation.get().isCanBeResolved = true
    configurations = mutableListOf(project.configurations.implementation.get()) as List<FileCollection>?
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