plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("application")
}

application {
    mainClass.set("org.gracenote.aggregator.AggregatorServiceApplication")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.commons:commons-csv:1.8")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}