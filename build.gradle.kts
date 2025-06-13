import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jlleitschuh.gradle.ktlint") version "13.0.0-rc.1"

    val kotlinVersion = "2.1.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

group = "com.study"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val kotestVersion = "5.9.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.h2database:h2")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

configure<KtlintExtension> {
    // ktlint에 넘길 에디터컨피그(editorconfig) 옵션들을 맵 형태로 지정
    additionalEditorconfig.set(
        mapOf(
            // 코드 스타일을 IntelliJ IDEA 기본 스타일로 맞춘다
            "ktlint_code_style" to "intellij_idea",

            // 한 줄의 최대 길이를 250자로 허용
            "max_line_length" to "250",

            // import-on-demand (와일드카드 import, e.g. kotlin.*)를 허용할 패키지 패턴
            "ij_kotlin_packages_to_use_import_on_demand" to "**",

            // 함수 호출부(call site) 끝에 오는 trailing comma(끝쉼표)를 금지
            "ij_kotlin_allow_trailing_comma_on_call_site" to "false",

            // 컬렉션 리터럴, 함수 선언 등 일반적인 곳에서의 trailing comma는 허용
            "ij_kotlin_allow_trailing_comma" to "true",

            // 클래스 시그니처에 파라미터가 1개 이상이면 자동으로 줄바꿈(멀티라인) 강제
            "ktlint_class_signature_rule_force_multiline_when_parameter_count_greater_or_equal_than" to "1",

            // 함수 시그니처에 파라미터가 2개 이상이면 자동으로 줄바꿈(멀티라인) 강제
            "ktlint_function_signature_rule_force_multiline_when_parameter_count_greater_or_equal_than" to "2"
        )
    )
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
