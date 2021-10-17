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
                api(Dependencies.Jetbrains.KotlinX.Coroutines.core)
                api(Dependencies.Jetbrains.KotlinX.DateTime.dateTime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":core:test"))
            }
        }
    }
}
