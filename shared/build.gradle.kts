plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
}

kotlin {
    androidTarget()
    jvmToolchain(17)
}

android {
    namespace = "com.krasovitova.currencywallet.shared"
    compileSdk = 36
    defaultConfig.minSdk = 26
}
