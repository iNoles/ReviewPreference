plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    namespace = "com.jonathansteele.reviewpreference"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.jonathansteele.reviewpreference"
            artifactId = "reviewpreference"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

dependencies {
    api("com.google.android.play:review-ktx:2.0.1")
    api("androidx.preference:preference-ktx:1.2.1")
}
