plugins {
    id(Dependencies.Android.Plugin.application)
    kotlin("android")
}
dependencies {
    implementation(project(":samples:iceandfire:app"))
    implementation(Dependencies.Jetbrains.KotlinX.Coroutines.android)
    implementation(Dependencies.Jetpack.AppCompat.appcompat)
    implementation(platform(Dependencies.Jetpack.Compose.bom))
    implementation(Dependencies.Jetpack.Compose.ui)
    implementation(Dependencies.Jetpack.Compose.uiTooling)
    implementation(Dependencies.Jetpack.Compose.material)
    implementation(Dependencies.Jetpack.Navigation.compose)
    implementation(Dependencies.Ktor.okhttp)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}
android {
    lint {
        abortOnError = false
        // no idea why, but this is needed. should try to remove it from time to time.
        disable.add("DialogFragmentCallbacksDetector")
    }
    defaultConfig {
        applicationId = "com.jonatbergn.iceandfire"
        minSdk = Dependencies.Android.MinSdk.version
        targetSdk = Dependencies.Android.TargetSdk.version
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildToolsVersion = Dependencies.Android.BuildTools.version
    compileSdk = Dependencies.Android.CompileSdk.version
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    packagingOptions {
        val excludes = listOf(
            "META-INF/**",
            "default/**"
        )
        resources.excludes.addAll(excludes)
    }
    buildFeatures { compose = true }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}
