plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.nibm.loon"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nibm.loon"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation (libs.androidx.material.v151)
    implementation (libs.ui)
    implementation (libs.coil.compose.v230)

    // Compose Dependencies
    implementation (libs.ui)
    implementation (libs.androidx.compose.material.material)
    implementation (libs.ui.tooling.preview)
    implementation (libs.androidx.activity.compose.v181)

    // Update or add these compose-related dependencies
    implementation(platform(libs.androidx.compose.bom.v20231001))
    implementation(libs.androidx.compose.ui.ui2)
    implementation(libs.ui.graphics)
    implementation(libs.material3)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.runtime)

    // If you need image loading capabilities
    implementation(libs.coil.kt.coil.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.androidx.activity.activity.compose.v172.x3)
    implementation (libs.androidx.ui.v161)
    implementation (libs.androidx.material.v161)
    implementation (libs.androidx.ui.tooling.preview.v161)
    implementation (libs.androidx.navigation.compose)

    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-auth")

}