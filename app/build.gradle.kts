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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.coil.compose.v230)

// Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.androidx.compose.bom.v20231001))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.ui2)
    implementation(libs.androidx.compose.material.material)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.navigation.compose)

// UI dependencies
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

// Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //noinspection GradleDependency
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation ("com.google.firebase:firebase-database:20.0.6")


    implementation ("androidx.compose.ui:ui:1.2.0")
    implementation ("androidx.compose.material:material:1.2.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.2.0")
    implementation ("androidx.compose.material:material-icons-extended:1.2.0")
    implementation ("androidx.navigation:navigation-compose:2.5.0")
    implementation ("androidx.activity:activity-compose:1.5.0")

    // Optional - for preview support
    debugImplementation ("androidx.compose.ui:ui-tooling:1.2.0")

}