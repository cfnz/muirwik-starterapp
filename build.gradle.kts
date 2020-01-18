import com.ccfraser.gradle.GradleWebpackPluginSettings
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

group = "com.ccfraser.muirwik"
version = "0.2.4"
description = "Starter Application for Muirwik (a Material UI React wrapper written in Kotlin)"

val productionConfig: Boolean = (properties["production"] as String).toBoolean()

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.3.60"

    var muirwikComponentVersion: String by extra
    @Suppress("UNUSED_VALUE") // We don't use this value here, but it used down below.
    muirwikComponentVersion = "0.4.1"

    repositories {
        jcenter()
        maven { setUrl("https://dl.bintray.com/cfraser/gradle-webpack-plugin") }
    }

    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath("com.ccfraser.gradle:gradle-webpack-plugin:0.3.1")
    }
}

apply {
    plugin("kotlin2js")
    plugin("kotlin-dce-js")
    plugin("com.ccfraser.gradle.gradle-webpack-plugin")
}

plugins {
    java // Not sure why this is needed, but it makes "compile(...)" lines down below work
    id("com.moowork.node") version "1.2.0"
}

val kotlinVersion: String by extra
val muirwikComponentVersion: String by extra

repositories {
    jcenter()
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-dev") }
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-js-wrappers") }
//    maven { setUrl("https://dl.bintray.com/cfraser/muirwik") }
    mavenLocal()
}

dependencies {
    val kotlinJsVersion = "pre.89-kotlin-$kotlinVersion"
    val kotlinReactVersion = "16.9.0-$kotlinJsVersion"

    implementation(kotlin("stdlib-js", kotlinVersion))

    implementation("org.jetbrains", "kotlin-react", kotlinReactVersion)
    implementation("org.jetbrains", "kotlin-react-dom", kotlinReactVersion)
    implementation("org.jetbrains", "kotlin-styled", "1.0.0-$kotlinJsVersion")

    implementation("com.ccfraser.muirwik:muirwik-components:$muirwikComponentVersion")
}

val compileKotlin2Js: Kotlin2JsCompile by tasks
compileKotlin2Js.kotlinOptions {
    sourceMap = true
    metaInfo = true
    outputFile = "${project.buildDir.path}/js/app.js"
    main = "call"
    moduleKind = "commonjs"
}

val runDceKotlinJs: KotlinJsDce by tasks
runDceKotlinJs.apply {
    // Turns out that when devMode is true, it still copies all the required js modules but does not strip any
    // code from them... just what we were doing with our copyJsForBundle task!
    dceOptions.devMode = !productionConfig
    dceOptions.outputDirectory = "${buildDir}/js-for-bundle"
    keep.add("kotlin.defineModule")
}

configure<GradleWebpackPluginSettings> {
    production = productionConfig
}

