plugins {
    id("com.android.application")
    kotlin("android")
}
dependencies {
    implementation(project(":samples:cars"))
    implementation(enforcedPlatform(libs.androidx.compose.bom))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.ktor.client.okhttp)
    implementation("com.google.android.material:material:1.8.0-rc01")
    implementation("io.coil-kt:coil-compose:2.2.2")
    coreLibraryDesugaring(libs.android.tools.desugarJdk)
}
android {
    defaultConfig {
        applicationId = "com.jonatbergn.cars"
        minSdkPreview = libs.versions.android.build.sdk.min.get()
        targetSdkPreview = libs.versions.android.build.target.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildToolsVersion = libs.versions.android.build.tools.get()
    compileSdkVersion = libs.versions.android.build.sdk.compile.get()
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
