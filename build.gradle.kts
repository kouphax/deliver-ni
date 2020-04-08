
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.70"
    id("org.flywaydb.flyway") version "6.3.3"
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
    sourceCompatibility = JavaVersion.VERSION_11
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
    implementation("org.http4k:http4k-format-moshi:3.242.0")

    implementation("org.postgresql:postgresql:42.2.12")
    implementation("com.zaxxer:HikariCP:2.7.8")
    implementation("org.jetbrains.exposed:exposed-core:0.23.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.23.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.23.1")
    implementation("org.slf4j:slf4j-simple:1.7.26")

    implementation("org.koin:koin-core:2.1.5")

    implementation("io.github.microutils:kotlin-logging:1.7.9")

    testImplementation("org.koin:koin-test:2.1.5")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "se.yobriefca.deliveries.api.AppKt"
}

tasks {
    val stage by creating {
        dependsOn("installDist")
    }
}

