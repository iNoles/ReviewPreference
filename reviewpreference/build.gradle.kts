import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

val githubProperties = Properties()
File(rootDir, "github.properties").takeIf { it.exists() }?.inputStream()?.use(githubProperties::load)

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/inoles/reviewpreference")
            credentials {
                username = githubProperties["gpr.user"] as String? ?: System.getenv("USERNAME")
                password = githubProperties["gpr.key"] as String? ?: System.getenv("PASSWORD")
            }
        }
    }
    publications {
        register<MavenPublication>("release") {
            groupId = "com.jonathansteele.reviewpreference"
            artifactId = "reviewpreference"
            version = "1.0"
            artifact("${layout.buildDirectory.get()}/outputs/aar/reviewpreference-release.aar")
        }
    }
}

dependencies {
    api("com.google.android.play:review-ktx:2.0.1")
    api("androidx.preference:preference-ktx:1.2.1")
}
