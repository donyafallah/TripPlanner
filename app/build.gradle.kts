

plugins {

    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "ir.shariaty.tripplaner"
    compileSdk = 35

    defaultConfig {
        applicationId = "ir.shariaty.tripplaner"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



    // 1. ابتدا Firebase BoM را برای مدیریت نسخه ها اضافه کنید (فقط یک بار)
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))


    // کتابخانه احراز هویت (Authentication)
    implementation("com.google.firebase:firebase-auth")

    // کتابخانه ورود با گوگل (Google Sign-In)
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // (اختیاری) برای جمع آوری آمار استفاده از برنامه
    implementation("com.google.firebase:firebase-analytics")
}