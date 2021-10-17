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
                api(project(":core"))
                api(Dependencies.Jetbrains.KotlinX.Coroutines.core)
                api(Dependencies.Jetbrains.KotlinX.DateTime.dateTime)
                api(Dependencies.Jetbrains.KotlinX.Serialization.json)
                api(Dependencies.Jetbrains.Kotlin.Test.junit)
                api(Dependencies.Jetbrains.Kotlin.Test.common)
                api(Dependencies.Jetbrains.Kotlin.Test.annotations)
                api(Dependencies.CashApp.Turbine.turbine)
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
