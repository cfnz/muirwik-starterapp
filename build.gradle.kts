group = "com.ccfraser.muirwik"
version = "0.8.2"
description = "Starter Application for Muirwik (a Material UI React wrapper written in Kotlin)"

plugins {
    kotlin("js") version "1.5.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    val kotlinVersion = "1.5.0"
    val muirwikComponentVersion = "0.8.2"
    val kotlinJsVersion = "pre.204-kotlin-$kotlinVersion"

    implementation(kotlin("stdlib-js", kotlinVersion))
    implementation("org.jetbrains.kotlin-wrappers", "kotlin-styled", "5.3.0-$kotlinJsVersion")
    implementation(npm("react-hot-loader", "^4.12.20"))
    implementation("com.ccfraser.muirwik:muirwik-components:$muirwikComponentVersion")
}

kotlin {
    // At time of writing, js(IR) does not support incremental compilation, so during development
    // using the Legacy compiler gives quicker edit-rebuild-view iterations. Both Legacy and IR should
    // work with Muirwik.
    js(LEGACY) {
//    js(IR) {
        useCommonJs()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
}
