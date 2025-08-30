plugins {
    alias(libs.plugins.agp.lib)
}

android {
    namespace = "com.rosan.hidden_api"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_21
        sourceCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
}