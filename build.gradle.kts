plugins {
    `java-library`
    id("minestom.publishing-conventions")
    id("minestom.native-conventions")
}

allprojects {
    group = "net.minestom.server"
    version = "1.0"
    description = "Lightweight and multi-threaded Minecraft server implementation"
}

sourceSets {
    main {
        java {
            srcDir(file("src/autogenerated/java"))
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks {
    withType<Javadoc> {
        (options as? StandardJavadocDocletOptions)?.apply {
            encoding = "UTF-8"

            // Custom options
            addBooleanOption("html5", true)
            addStringOption("-release", "17")
            // Links to external javadocs
            links("https://docs.oracle.com/en/java/javase/17/docs/api/")
            links("https://jd.adventure.kyori.net/api/${libs.versions.adventure.get()}/")
        }
    }
    withType<Zip> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

dependencies {
    // Testing Framework
    testImplementation(project(mapOf("path" to ":testing")))
    // Only here to ensure J9 module support for extensions and our classloaders
    testCompileOnly(libs.mockito.core)


    // Logging
    implementation(libs.bundles.logging)
    // Libraries required for the terminal
    implementation(libs.bundles.terminal)

    // Performance improving libraries
    implementation(libs.caffeine)
    api(libs.fastutil)
    implementation(libs.bundles.flare)

    // Libraries
    api(libs.gson)
    implementation(libs.jcTools)
    // Path finding
    api(libs.hydrazine)

    // Adventure, for user-interface
    api(libs.bundles.adventure)

    // Kotlin Libraries
    api(libs.bundles.kotlin)

    // Extension Management System dependency handler.
    api(libs.dependencyGetter)

    // Minestom Data (From MinestomDataGenerator)
    implementation(libs.minestomData)

    // NBT parsing/manipulation/saving
    api("io.github.jglrxavpok.hephaistos:common:${libs.versions.hephaistos.get()}")
    api("io.github.jglrxavpok.hephaistos:gson:${libs.versions.hephaistos.get()}")
}
