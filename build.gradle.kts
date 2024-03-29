group = "com.ccfraser.muirwik"
version = "0.10.1"
description = "Starter Application for Muirwik (a Material UI React wrapper written in Kotlin)"

plugins {
    kotlin("js") version "1.6.10"
}

repositories {
//    mavenLocal()
    mavenCentral()
}

dependencies {
    val kotlinVersion = "1.6.10"
    val muirwikComponentVersion = "0.10.1"
    val kotlinJsVersion = "pre.290-kotlin-$kotlinVersion"

    implementation(kotlin("stdlib-js", kotlinVersion))
    implementation("org.jetbrains.kotlin-wrappers", "kotlin-styled", "5.3.3-$kotlinJsVersion")
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
