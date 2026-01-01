// --------------------------------------
// Plugins
// --------------------------------------
plugins {
    // Spring Boot 플러그인
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"

    // Kotlin 관련 플러그인
    val kotlinVersion = "2.3.0"
    kotlin("jvm") version kotlinVersion              // Kotlin JVM
    kotlin("kapt") version kotlinVersion            // Kotlin Annotation Processor
    kotlin("plugin.spring") version kotlinVersion   // Spring 지원

    application
}

// --------------------------------------
// Project metadata
// --------------------------------------
group = "com.pickme"
version = "2.2.0-SNAPSHOT"

// --------------------------------------
// Project Main Class
// --------------------------------------
application {
    mainClass.set("com.pickme.calendar.CalendarApplicationKt")
}

// --------------------------------------
// Java & Kotlin toolchain
// --------------------------------------
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))  // Java Toolchain
    }
}

kotlin {
    jvmToolchain(21)  // Kotlin Toolchain
}

// --------------------------------------
// Configurations
// --------------------------------------
val compileOnly by configurations.getting
val annotationProcessor by configurations.getting

// compileOnly가 annotationProcessor를 상속하도록 설정
compileOnly.extendsFrom(annotationProcessor)

// --------------------------------------
// KAPT 설정
// --------------------------------------
kapt {
    correctErrorTypes = true
    includeCompileClasspath = false
}

// --------------------------------------
// Repositories
// --------------------------------------
repositories {
    mavenCentral()
}

// --------------------------------------
// Dependencies
// --------------------------------------
dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("stdlib"))

    // Swagger / OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

    // .env
    implementation("io.github.cdimascio:java-dotenv:5.2.2")

    // JWT
    implementation("com.auth0:java-jwt:4.4.0")

    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
}
