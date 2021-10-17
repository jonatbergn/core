import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Android.Plugin.gradle)
        classpath(Dependencies.Jetbrains.Kotlin.Plugin.gradle)
        classpath(Dependencies.Jonatbergn.JacocoConfig.Plugin.gradle)
    }
}
plugins {
    id(Dependencies.GitHub.BenManes.Versions.Plugin.id) version Dependencies.GitHub.BenManes.Versions.version
    id(Dependencies.Jonatbergn.JacocoConfig.Plugin.id) version Dependencies.Jonatbergn.JacocoConfig.version
    kotlin("plugin.serialization") version Dependencies.Jetbrains.Kotlin.version
}
allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    tasks.withType<DependencyUpdatesTask> {
        rejectVersionIf {
            isNonStable(candidate.version) && !isNonStable(currentVersion)
        }
    }
}
extensions.configure<com.jonatbergn.jacoco.JacocoConfigExtension> {
    isCsvEnabled = false
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "$VERSION_1_8"
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xallow-jvm-ir-dependencies",
        )
    }
}
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("@webpack-cli/serve", "1.5.2")
    }
}
task("iceandfireWeb").dependsOn(":samples:iceandfire:app:web:browserDevelopmentRun")
task("iceandfireAndroid").dependsOn(":samples:iceandfire:app:android:installDebug")
