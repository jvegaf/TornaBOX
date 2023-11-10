plugins {
    application
}

group 'me.jvegaf'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.cdimascio:dotenv-java:3.0.0'
    implementation 'net.sourceforge.htmlunit:htmlunit:2.70.0'
    implementation 'org:jaudiotagger:2.0.3'
    implementation 'com.sachinhandiekar:jMusixMatch:1.1.4'
    implementation "fr.brouillard.oss:cssfx:11.5.1"
    implementation 'se.michaelthelin.spotify:spotify-web-api-java:8.0.0'
    testImplementation 'org.slf4j:slf4j-log4j12:2.0.7'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.2'
    testImplementation 'org.awaitility:awaitility:4.2.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.0'
}

test {
    useJUnitPlatform()
}

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    mavenCentral()
}

//Resolve the used operating system
var currentOS = org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem()
var platform = ""
if (currentOS.isMacOsX) {
    platform = "mac"
} else if (currentOS.isLinux) {
    platform = "linux"
} else if (currentOS.isWindows) {
    platform = "win"
}

val javaFXVersion = "17.0.0.1"
val appClassName = "dev.vulture.packagefx.App"
val appModuleName = "dev.vulture.packagefx"

val compiler = javaToolchains.compilerFor {
    languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
}

dependencies {
    implementation("org.openjfx:javafx-base:${javaFXVersion}:${platform}")
    implementation("org.openjfx:javafx-controls:${javaFXVersion}:${platform}")
    implementation("org.openjfx:javafx-graphics:${javaFXVersion}:${platform}")
    implementation("org.openjfx:javafx-fxml:${javaFXVersion}:${platform}")
    implementation ( "io.github.cdimascio:dotenv-java:3.0.0" )
    implementation ( "net.sourceforge.htmlunit:htmlunit:2.70.0" )
    implementation ( "org:jaudiotagger:2.0.3" )
    implementation ( "com.sachinhandiekar:jMusixMatch:1.1.4" )
    implementation ( "fr.brouillard.oss:cssfx:11.5.1" )
    implementation ( "se.michaelthelin.spotify:spotify-web-api-java:8.0.0" )
    testImplementation ( "org.slf4j:slf4j-log4j12:2.0.7" )
    testImplementation ( "org.junit.jupiter:junit-jupiter-api:5.8.2" )
    testImplementation ( "org.awaitility:awaitility:4.2.0" )
    testRuntimeOnly ( "org.junit.jupiter:junit-jupiter-engine:5.10.0" )
}

application {
    // Define the main class for the application.
    mainModule.set("dev.vulture.packagefx")
    mainClass.set(appClassName)
    if(platform.equals("mac")) {
        applicationDefaultJvmArgs = listOf("-Dsun.java2d.metal=true")
    }
}

java {
    modularity.inferModulePath.set(true)
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    task<Copy>("copyDependencies") {
        from(configurations.runtimeClasspath)
        into("$buildDir/modules")
    }

    task<Exec>("package") {
        dependsOn(listOf("build", "copyDependencies"))
        val jdkHome = compiler.get().metadata.installationPath.asFile.absolutePath
        commandLine("${jdkHome}/bin/jpackage")
        args(listOf(
                "-n", "fxBuildDemo",
                "-p", "$buildDir/modules"+File.pathSeparator+"$buildDir/libs",
                "-d", "$buildDir/installer",
                "-m", "${appModuleName}/${appClassName}"))
    }

}
