plugins {
    kotlin("js")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
        }
        binaries.executable()
    }
}
dependencies {
    implementation(project(":samples:iceandfire:app"))

    val group = "org.jetbrains.kotlin-wrappers"
    val bom = "$group:kotlin-wrappers-bom:1.0.0-pre.467"
    val ext = "$group:kotlin-extensions"
    val react = "$group:kotlin-react"
    val reactDom = "$group:kotlin-react-dom"
    implementation(enforcedPlatform(bom))
    implementation(ext)
    implementation(react)
    implementation(reactDom)

    implementation(npm("react", "18.2.0"))
    implementation(npm("react-dom", "18.2.0"))
    implementation(npm("postcss", "8.4.20"))
    implementation(npm("postcss-loader", "7.0.2"))
    implementation(npm("autoprefixer", "10.4.13"))
    implementation(npm("tailwindcss", "3.2.4"))
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
