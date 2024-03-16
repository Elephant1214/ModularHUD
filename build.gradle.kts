import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("fabric-loom") version ("1.5-SNAPSHOT")
    id("io.gitlab.arturbosch.detekt") version("1.23.5")
}

version = properties["mod_version"]!! as String
group = properties["maven_group"]!! as String

base {
    archivesName = properties["archives_base_name"]!! as String
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create("modularhud") {
            sourceSet(sourceSets["main"])
            sourceSet(sourceSets["client"])
        }
    }
}

repositories {}

dependencies {
    minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${properties["fabric_version"]}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${properties["fabric_kotlin_version"]}")
    // Uncomment the following line to enable the deprecated Fabric API modules.
    // Even though these are deprecated, they're still included in the current API.
    // modImplementation "net.fabricmc.fabric-api:fabric-api-deprecated:${project.fabric_version}"
}

detekt {
    toolVersion = "1.23.5"
    config.setFrom(file("detekt.yml"))
    buildUponDefaultConfig = true
}

tasks {
    named<Copy>("processResources") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from(sourceSets["main"].resources.srcDirs) {
            include("fabric.mod.json")
            expand(project.properties)
        }

        from(sourceSets["main"].resources.srcDirs) {
            exclude("fabric.mod.json")
        }
    }

    withType<KotlinCompile>().all {
        kotlinOptions {
            freeCompilerArgs += listOf(
                "-opt-in=kotlin.ExperimentalStdlibApi"
            )
        }
    }
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlinx-serialization")

    repositories {
        mavenCentral()
    }

    java {
        withSourcesJar()
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "17"

            all {
                kotlinOptions.freeCompilerArgs += listOf(
                    "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
                )
            }
        }

        withType<JavaCompile> {
            sourceCompatibility = "17"
            targetCompatibility = "17"
            options.encoding = "UTF-8"
            options.release.set(17)
        }
    }
}
