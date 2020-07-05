val logback_version: String by project
val kotlin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.70"
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
}