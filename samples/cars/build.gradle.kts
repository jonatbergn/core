plugins {
    kotlin("multiplatform")
    alias(libs.plugins.serialization)
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
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.bundles.ktorClientCommon)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.testCommon)
                implementation(project(":samples:cars:fake"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.kotlin.test.junit)
            }
        }
    }
}
