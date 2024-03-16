rootProject.name = "ModularHUD"

pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/")
		mavenCentral()
		gradlePluginPortal()
	}

	plugins {
		kotlin("jvm") version ("1.9.22")
		kotlin("plugin.serialization") version ("1.9.22")
	}
}
