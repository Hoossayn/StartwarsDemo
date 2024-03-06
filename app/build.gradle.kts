plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.startwarsdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.startwarsdemo"
        minSdk = 24
        targetSdk = 34
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val coreKtx = "1.3.2"
    val appCompact = "1.2.0"
    val material = "1.3.0"
    val constraintLayout = "2.0.4"
    val retrofit = "2.9.0"
    val okhttp = "4.9.0"
    val gson = "2.8.6"
    val dagger = "2.48"
    val viewmodel = "2.3.0"
    val coroutine = "1.4.2"
    val stetho = "1.5.1"
    val testCoreRunner = "1.2.0"
    val espresso = "3.3.0"
    val googleTruth = "1.0"
    val barista = "3.7.0"
    val androidxJunit = "1.1.2"
    val junit = "4.13.2"
    val androidxArchTest = "2.1.0"


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    implementation ("androidx.lifecycle:lifecycle-viewmodel:$viewmodel")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewmodel")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$viewmodel")

    //Retrofit2
    implementation ("com.squareup.retrofit2:retrofit:$retrofit")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit")

    //Okhttp3
    implementation ("com.squareup.okhttp3:okhttp:$okhttp")

    //Gson
    implementation ("com.google.code.gson:gson:$gson")

    //Dagger
    implementation ("com.google.dagger:hilt-android:$dagger")

   // kapt "com.google.dagger:hilt-android-compiler:$dagger"

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine")

    //Stetho
    implementation ("com.facebook.stetho:stetho:$stetho")
    implementation ("com.facebook.stetho:stetho-okhttp3:$stetho")

    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.13")

    //TEST SUITE
    androidTestImplementation ("androidx.test:runner:$testCoreRunner")
    androidTestImplementation ("androidx.test.espresso:espresso-core:$espresso")
    androidTestImplementation ("androidx.test.ext:junit:$androidxJunit")
    androidTestImplementation("com.schibsted.spain:barista:$barista") {
       // exclude group: ("org.jetbrains.kotlin")
    }

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}