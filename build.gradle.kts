plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "me.jvegaf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val javaFXVersion = "25.0.4"
val appClassName = "me.jvegaf.tornabox.App"
val appModuleName = "me.jvegaf.tornabox"

val compiler =
    javaToolchains.compilerFor {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_25.majorVersion))
    }

javafx {
    version = javaFXVersion
    modules("javafx.base", "javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.media")
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation("net.sourceforge.htmlunit:htmlunit:2.70.0")
    implementation("org:jaudiotagger:2.0.3")
    implementation("com.sachinhandiekar:jMusixMatch:1.1.4")
    implementation("fr.brouillard.oss:cssfx:11.5.1")
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.awaitility:awaitility:4.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set(appClassName)
    // Silence the restricted-method warning printed by JavaFX 25+ native loading.
    applicationDefaultJvmArgs = listOf("--enable-native-access=javafx.graphics")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_25.majorVersion))
    }
}

tasks {
    register<Copy>("copyDependencies") {
        from(configurations.runtimeClasspath)
        into(layout.buildDirectory.dir("modules"))
    }

    register<Exec>("package") {
        dependsOn(listOf("build", "copyDependencies"))
        val jdkHome =
            compiler
                .get()
                .metadata.installationPath.asFile.absolutePath
        val buildDir = layout.buildDirectory.get().asFile
        commandLine("$jdkHome/bin/jpackage")
        args(
            listOf(
                "-n",
                "fxBuildDemo",
                "-p",
                "$buildDir/modules" + File.pathSeparator + "$buildDir/libs",
                "-d",
                "$buildDir/installer",
                "-m",
                "$appModuleName/$appClassName",
            ),
        )
    }
}
