buildscript {
	repositories {
		mavenLocal()
		mavenCentral()
	}
}

plugins {
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	`java-library`
	`maven-publish`
}

val artifactoryUser: String? by project
val artifactoryPass: String? by project
group = "com.vfs"
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter:2.6.7")
	implementation("org.springframework.boot:spring-boot-starter-web:2.6.7")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "16"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

java {
	withSourcesJar()
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = project.group.toString()
			artifactId = project.name
			version = project.property("version") as String

			from(components["java"])
		}
	}
}