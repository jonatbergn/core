plugins {
    kotlin("js")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }
}
dependencies {
    implementation(project(":samples:iceandfire:app"))
    implementation(enforcedPlatform(Dependencies.Jetbrains.KotlinWrappers.bom))
    implementation(Dependencies.Jetbrains.KotlinWrappers.ext)
    implementation(Dependencies.Jetbrains.KotlinWrappers.react)
    implementation(Dependencies.Jetbrains.KotlinWrappers.reactDom)
    implementation(npm("react", "17.0.2"))
    implementation(npm("react-dom", "17.0.2"))
    implementation(npm("postcss", "8.2.6"))
    implementation(npm("postcss-loader", "4.2.0")) // 5.0.0 seems not to work
    implementation(npm("autoprefixer", "10.2.4"))
    implementation(npm("tailwindcss", "2.0.3"))
}

val copyTailwindConfig = tasks.register<Copy>("copyTailwindConfig") {
    from("./tailwind.config.js")
    into("${rootProject.buildDir}/js/packages/${rootProject.name}-${project.name}")

    dependsOn(":kotlinNpmInstall")
}

val copyPostcssConfig = tasks.register<Copy>("copyPostcssConfig") {
    from("./postcss.config.js")
    into("${rootProject.buildDir}/js/packages/${rootProject.name}-${project.name}")

    dependsOn(":kotlinNpmInstall")
}

tasks.named("processResources") {
    dependsOn(copyTailwindConfig)
    dependsOn(copyPostcssConfig)
}

afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
    }
}
