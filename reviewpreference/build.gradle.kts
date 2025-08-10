import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

val githubProperties = Properties()
File(rootDir, "github.properties").takeIf { it.exists() }?.inputStream()?.use(githubProperties::load)

android {
    namespace = "com.github.inoles.reviewpreference"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        consumerProguardFiles("consumer-proguard-rules.pro")
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
                username = githubProperties["gpr.user"] as String? ?: System.getenv("GITHUB_USER")
                password = githubProperties["gpr.key"] as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.inoles.reviewpreference"
            artifactId = "reviewpreference"
            version = "1.0.1"
            artifact("${layout.buildDirectory.get()}/outputs/aar/reviewpreference-release.aar")
        }
    }
}

dependencies {
    api(libs.review.ktx)
    api(libs.preference.ktx)
}
