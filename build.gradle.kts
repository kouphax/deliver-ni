import com.moowork.gradle.node.npm.NpmTask

val spek_version = "2.0.10"
val kotlin_version = "1.3.70"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.70"
    id("org.flywaydb.flyway") version "6.3.3"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("com.moowork.node") version "1.3.1"
    application
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.2.12")
    }
}

flyway {
    url = "jdbc:postgresql://localhost/jamhughes"
    user = "jamhughes"
    password = ""
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.http4k:http4k-core:3.242.0")
    implementation("org.http4k:http4k-server-jetty:3.242.0")
    implementation("org.http4k:http4k-format-jackson:3.242.0")
    implementation("org.http4k:http4k-metrics-micrometer:3.242.0")
    implementation("io.micrometer:micrometer-core:1.4.1")
    implementation("org.postgresql:postgresql:42.2.12")
    implementation("com.zaxxer:HikariCP:2.7.8")
    implementation("org.jetbrains.exposed:exposed-core:0.23.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.23.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.23.1")
    implementation("org.slf4j:slf4j-simple:1.7.26")
    implementation("org.koin:koin-core:2.1.5")
    implementation("io.github.microutils:kotlin-logging:1.7.9")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("org.http4k:http4k-testing-hamkrest:3.242.0")
    testImplementation("io.mockk:mockk:1.9.3")
}

application {
    mainClassName = "se.yobriefca.deliveries.api.AppKt"
}

node {
    version = "13.3.0"
}

tasks {

    test {
        useJUnitPlatform()
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    // required for heroku mostly
    register<NpmTask>("testClient") {
        group = "client"
        setWorkingDir("client")
        setEnvironment(mapOf("CI" to "true"))
        setArgs(listOf("test"))
    }

    register<NpmTask>("buildClient") {
        group = "client"
        setWorkingDir(file("client"))
        setArgs(listOf("run", "build"))
    }

    register<Copy>("copyClient") {
        group = "client"
        from("./client/build")
        into("src/main/resources/public")
    }

    register<NpmTask>("prepareClient") {
        group = "client"
        setWorkingDir(file("client"))
        setArgs(listOf("install"))
    }

    register<GradleBuild>("installClient") {
        group = "client"
        dependsOn("testClient", "buildClient", "copyClient")
    }

    installDist {
        dependsOn("installClient")
    }

    register<GradleBuild>("stage") {
        group = "heroku"
        dependsOn(
            "prepareClient",
            "installClient",
            installDist
        )
    }
}
