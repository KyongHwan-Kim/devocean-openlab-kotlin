import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	// jpa no-args constructor
	kotlin("plugin.jpa") version "1.9.0"
	id("org.openapi.generator") version "6.2.0"
	// query dsl
	kotlin("kapt") version "1.8.10"
}

group = "com.openlab"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("javax.servlet:javax.servlet-api:4.0.1")
	implementation("org.apache.httpcomponents.client5:httpclient5:5.2.1")
	// kafka dependency
	implementation("org.springframework.kafka:spring-kafka")
	// jpa dependency
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("mysql:mysql-connector-java")
	// query dsl dependency
	implementation("com.querydsl:querydsl-jpa")
	kapt("com.querydsl:querydsl-apt:4.4.0:jpa")
	// redis dependency
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	// openapi validator
	implementation("io.swagger.parser.v3:swagger-parser-v3:2.0.31")


	testImplementation("it.ozimov:embedded-redis:0.7.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	// test kafka dependency
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
// query dsl task
kapt {
	correctErrorTypes = true
}
// openapi 문서를 Generate하기 위한 설정
task<GenerateTask>("generateApiDoc") {
	generatorName.set("html2")
	inputSpec.set("$projectDir/src/main/resources/spec-docs/openapi.yaml")
	outputDir.set("$buildDir/openapi/doc/")
}

task<GenerateTask>("generateApiServer") {
	generatorName.set("kotlin-spring")
	inputSpec.set("$projectDir/src/main/resources/spec-docs/openapi.yaml")
	outputDir.set("$buildDir/openapi/server-code/")
	apiPackage.set("com.openlab.openapi_platform.openapi.generated.controller")
	modelPackage.set("com.openlab.openapi_platform.openapi.generated.model")
	configOptions.set(
		mapOf(
			"interfaceOnly" to "true",
		)
	)

	additionalProperties.set(
		mapOf(
			"useTags" to "true"
		)
	)

	typeMappings.set(mapOf("DateTime" to "LocalDateTime"))
	importMappings.set(mapOf("LocalDateTime" to "java.time.LocalDateTime"))
}

tasks.compileKotlin {
	dependsOn("generateApiServer")
}

kotlin.sourceSets.main {
	kotlin.srcDirs("src/main/kotlin", "$buildDir/openapi/server-code/src/main")
}