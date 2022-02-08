plugins {
    kotlin("jvm") version "1.6.10"
}

group = "com.ydanneg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://pdftron.com/maven/release/")
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("com.pdftron:PDFNet:9.2.0")
    testImplementation(kotlin("test"))
}