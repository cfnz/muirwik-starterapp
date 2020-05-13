group = "com.ccfraser.muirwik"
version = "0.5.1"
description = "Starter Application for Muirwik (a Material UI React wrapper written in Kotlin)"

plugins {
    kotlin("js") version "1.3.72"
}

repositories {
    jcenter()
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-dev") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-js-wrappers") }
    maven { setUrl("https://dl.bintray.com/cfraser/muirwik") }
//    mavenLocal()
}

dependencies {
    val kotlinVersion = "1.3.72"
    val muirwikComponentVersion = "0.5.1"
    val kotlinJsVersion = "pre.104-kotlin-$kotlinVersion"
    val kotlinReactVersion = "16.13.1-$kotlinJsVersion"

    implementation(kotlin("stdlib-js", kotlinVersion))

    implementation("org.jetbrains", "kotlin-react", kotlinReactVersion)
    implementation("org.jetbrains", "kotlin-react-dom", kotlinReactVersion)
    implementation("org.jetbrains", "kotlin-styled", "1.0.0-$kotlinJsVersion")
    implementation("org.jetbrains", "kotlin-css-js", "1.0.0-$kotlinJsVersion")
    implementation(npm("@material-ui/core", "^4.9.14"))
    implementation(npm("react-hot-loader", "^4.12.20"))

    implementation("com.ccfraser.muirwik:muirwik-components:$muirwikComponentVersion")
}

kotlin {
    target {
        useCommonJs()
        browser {
        }
    }
}
