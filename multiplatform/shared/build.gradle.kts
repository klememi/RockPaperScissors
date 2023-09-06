plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-10"
    kotlin("plugin.serialization") version "1.8.20"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "RPSKit"
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        val ktorVersion = "2.3.4"

        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
                implementation("org.mobilenativefoundation.store:store5:5.0.0-beta03")
                implementation("org.jetbrains.kotlinx:atomicfu:0.18.5")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }
        }
    }
}

android {
    namespace = "cz.livesport"
    compileSdk = 33
    defaultConfig {
        minSdk = 33
    }
}