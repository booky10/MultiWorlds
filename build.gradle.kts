plugins {
    id("java-library")
    id("maven-publish")
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

version = "3.0.0"
group = "dev.booky"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }

    runServer {
        minecraftVersion("1.19")
    }
}
