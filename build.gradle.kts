import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.tools.gradle)
        classpath(libs.kotlin.gradle)
    }
}
plugins {
    alias(libs.plugins.versions)
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
allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "$VERSION_11"
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
            )
        }
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
