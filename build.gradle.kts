group = "com.ccfraser.muirwik"
version = "0.6.3"
description = "Starter Application for Muirwik (a Material UI React wrapper written in Kotlin)"

plugins {
    kotlin("js") version "1.4.20"
}

repositories {
    jcenter()
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
//    mavenLocal()
}

dependencies {
    val kotlinVersion = "1.4.20"
    val muirwikComponentVersion = "0.6.3"
    val kotlinJsVersion = "pre.129-kotlin-$kotlinVersion"

    implementation(kotlin("stdlib-js", kotlinVersion))
    implementation("org.jetbrains", "kotlin-styled", "5.2.0-$kotlinJsVersion")
    implementation(npm("react-hot-loader", "^4.12.20"))
    implementation("com.ccfraser.muirwik:muirwik-components:$muirwikComponentVersion")
}

kotlin {
    js(LEGACY) {
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
