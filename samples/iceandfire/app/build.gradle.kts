plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}
kotlin {
    targets {
        jvm()
        js(IR) {
            browser()
            binaries.executable()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":samples:iceandfire:foundation"))
                implementation(Dependencies.Jetbrains.KotlinX.Serialization.json)
                implementation(Dependencies.Jetbrains.KotlinX.Coroutines.core)
                implementation(Dependencies.Ktor.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":core:test"))
                implementation(Dependencies.Jetbrains.Kotlin.Test.junit)
                implementation(Dependencies.Jetbrains.Kotlin.Test.common)
                implementation(Dependencies.Jetbrains.Kotlin.Test.annotations)
            }
        }
        val jvmTest by getting { dependencies { dependsOn(commonTest) } }
    }
}
