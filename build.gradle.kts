import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val projectGroup: String by project
val projectVersion: String by project

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.craigmiller160.gradle.defaults") version "1.1.0"
    id("com.diffplug.spotless") version "6.17.0"
    `maven-publish`
}

group = projectGroup
version = projectVersion
java.sourceCompatibility = JavaVersion.VERSION_19

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.flywaydb:flyway-core")
    implementation("org.postgresql:postgresql")
    testImplementation("io.craigmiller160:testcontainers-common:1.1.1")
    implementation("io.hypersistence:hypersistence-utils-hibernate-60:3.3.1")
    implementation("ws.schild:jave-all-deps:3.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        jvmTarget = "19"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
