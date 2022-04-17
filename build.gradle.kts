plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.6.1-SNAPSHOT"
}

group = "dev.plex"
version = "1.0"
description = "Emotes"

repositories {
    mavenCentral()

    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    library("org.json:json:20220320")
    library("commons-io:commons-io:2.11.0")
}

bukkit {
    name = "Emotes"
    version = project.version.toString()
    description = "Add emote commands to your Minecraft server"
    website = "https://plex.us.org"
    authors = listOf("Mafrans", "Telesphoreo")
    main = "dev.plex.emotes.Emotes"
    apiVersion = "1.17"
    softDepend = listOf("TotalFreedomMod", "Plex")
    commands {
        register("emotes") {
            description = "Lists emotes, alternatively mutes them"
            usage = "/<command> [list | mute | reload]"
            aliases = listOf("emoteshelp")
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}