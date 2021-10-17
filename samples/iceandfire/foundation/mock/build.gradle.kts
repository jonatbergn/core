plugins {
    kotlin("multiplatform")
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
                api(project(":core:test"))
                api(Dependencies.Jetbrains.Kotlin.Test.junit)
                api(Dependencies.Jetbrains.Kotlin.Test.common)
                api(Dependencies.Jetbrains.Kotlin.Test.annotations)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Dependencies.Ktor.okhttp)
                implementation(Dependencies.Ktor.logging)
            }
        }
    }
}
