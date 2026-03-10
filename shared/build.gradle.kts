plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    androidTarget()
    jvmToolchain(17)

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
            implementation("io.insert-koin:koin-core:4.1.1")
            implementation("io.insert-koin:koin-compose-viewmodel:4.1.1")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(project(":uikit"))
        }
        androidMain.dependencies {
            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
            implementation("androidx.room:room-ktx:2.8.4")
            implementation("io.insert-koin:koin-android:4.1.1")
        }
    }
}

android {
    namespace = "com.krasovitova.currencywallet.shared"
    compileSdk = 36
    defaultConfig.minSdk = 26
}

dependencies {
    add("kspAndroid", "androidx.room:room-compiler:2.8.4")
}
