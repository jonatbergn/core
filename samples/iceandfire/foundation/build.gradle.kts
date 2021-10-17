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
                api(project(":core"))
                api(project(":core:exception"))
                implementation(Dependencies.Jetbrains.KotlinX.Serialization.json)
                implementation(Dependencies.Ktor.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":samples:iceandfire:foundation:mock"))
                implementation(Dependencies.Jetbrains.Kotlin.Test.junit)
                implementation(Dependencies.Jetbrains.Kotlin.Test.common)
                implementation(Dependencies.Jetbrains.Kotlin.Test.annotations)
                implementation(Dependencies.Ktor.mock)
            }
        }
    }
}
