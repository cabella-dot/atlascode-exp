plugins {
	id("java")
	id("org.jetbrains.kotlin.jvm") version "2.1.0"
	id("org.jetbrains.intellij.platform") version "2.5.0"
	id("com.apollographql.apollo") version "4.3.2"
}

group = "com.atlassian"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
	intellijPlatform {
		defaultRepositories()
	}
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
	intellijPlatform {
		create("IC", "2025.1")
		testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

		// Add necessary plugin dependencies for compilation here, example:
		// bundledPlugin("com.intellij.java")
	}
	implementation("com.apollographql.apollo:apollo-runtime:4.3.2")
}

apollo {
	service("service") {
		packageName.set("io.atlassian.micros.agg")
	}
}

intellijPlatform {
	pluginConfiguration {
		ideaVersion {
			sinceBuild = "251"
		}

		changeNotes = """
      Initial version
    """.trimIndent()
	}
}

tasks {
	// Set the JVM compatibility versions
	withType<JavaCompile> {
		sourceCompatibility = "21"
		targetCompatibility = "21"
	}
	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions.jvmTarget = "21"
	}
}
