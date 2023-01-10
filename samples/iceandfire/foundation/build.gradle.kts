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
                api(libs.kotlinx.serialization.json)
                implementation(libs.bundles.ktorClientCommon)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(project(":samples:iceandfire:foundation:fake"))
                implementation(libs.bundles.testCommon)
                implementation(libs.kotlin.test.junit)
                implementation(libs.ktor.client.mock)
                implementation(libs.turbine)
            }
        }
    }
}
