object Dependencies {

    object Jetbrains {
        object Kotlin {
            const val group = "org.jetbrains.kotlin"
            const val version = "1.5.31"

            object Plugin {
                const val gradle = "$group:kotlin-gradle-plugin:$version"
            }

            object Test {
                const val junit = "$group:kotlin-test-junit:$version"
                const val common = "$group:kotlin-test-common:$version"
                const val annotations = "$group:kotlin-test-annotations-common:$version"
            }
        }

        object KotlinX {
            private const val group = "org.jetbrains.kotlinx"

            object Coroutines {
                const val version = "1.5.2"
                const val core = "$group:kotlinx-coroutines-core:$version"
                const val android = "$group:kotlinx-coroutines-android:$version"
            }

            object Serialization {
                private const val version = "1.3.0"
                const val json = "$group:kotlinx-serialization-json:$version"
            }

            object DateTime {
                private const val version = "0.3.1"
                const val dateTime = "$group:kotlinx-datetime:$version"
            }
        }

        object KotlinWrappers {

            private const val version = "0.0.1-pre.257-kotlin-1.5.31"
            private const val group = "org.jetbrains.kotlin-wrappers"

            const val bom = "$group:kotlin-wrappers-bom:$version"
            const val ext = "$group:kotlin-extensions"
            const val react = "$group:kotlin-react"
            const val reactDom = "$group:kotlin-react-dom"
        }
    }

    object Android {

        const val version = "7.0.3"

        object TargetSdk {
            const val version = 31
        }

        object CompileSdk {
            const val version = 31
        }

        object MinSdk {
            const val version = 21
        }

        object BuildTools {
            const val version = "30.0.2"
        }

        object Plugin {
            const val gradle = "com.android.tools.build:gradle:$version"
            const val application = "com.android.application"
            const val library = "com.android.library"
        }
    }

    object Jetpack {

        object AppCompat {
            const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        }

        object Compose {
            private const val group = "androidx.compose"
            const val version = "1.0.5"
            const val ui = "$group.ui:ui:$version"
            const val uiTooling = "$group.ui:ui-tooling:$version"
            const val material = "$group.material:material:$version"
        }

        object Navigation {
            const val compose = "androidx.navigation:navigation-compose:2.4.0-beta02"
        }
    }

    object Ktor {
        private const val group = "io.ktor"
        private const val version = "1.6.5"
        const val core = "$group:ktor-client-core:$version"
        const val json = "$group:ktor-client-json:$version"
        const val okhttp = "$group:ktor-client-okhttp:${version}"
        const val logging = "$group:ktor-client-logging:${version}"
        const val mock = "$group:ktor-client-mock:${version}"
    }

    object GitHub {
        object BenManes {
            object Versions {
                const val version = "0.39.0"

                object Plugin {
                    const val id = "com.github.ben-manes.versions"
                    const val gradle = "com.github.ben-manes:gradle-versions-plugin:$version"
                }
            }
        }
    }

    object CashApp {
        object Turbine {
            val turbine = "app.cash.turbine:turbine:0.7.0"
        }
    }

    object Jonatbergn {
        object JacocoConfig {
            private const val group = "com.jonatbergn.jacoco"
            const val version = "0.10.0"

            object Plugin {
                const val id = "com.jonatbergn.jacoco.jacoco-config"
                const val gradle = "$group:jacoco-config"
            }
        }
    }
}
