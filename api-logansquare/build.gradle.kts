plugins {
    id("com.android.library")
}

val versions: Map<String, *> by rootProject.extra
android {
    compileSdkVersion(versions["compileSdk"] as Int)

    defaultConfig {
        minSdkVersion(16)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(project(":storable"))
    api("net.danlew:android.joda:2.9.9.4")

    implementation("androidx.annotation:annotation:${versions["androidx"]}")
    implementation("com.squareup.retrofit2:retrofit:${versions["retrofit"]}")
    implementation("ru.touchin:logansquare:1.4.3")
}
