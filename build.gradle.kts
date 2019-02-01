import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.io.ByteArrayOutputStream

val production: Boolean = (properties["production"] as String).toBoolean()

group = "com.ccfraser.muirwik"
version = "0.2.0"
description = "Starter Application for Muirwik (a Material UI React wrapper written in Kotlin)"

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.3.20"

    var muirwikComponentVersion: String by extra
    @Suppress("UNUSED_VALUE") // We don't use this value here, but it used down below.
    muirwikComponentVersion = "0.2.0"

    repositories {
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

apply {
    plugin("kotlin2js")
    if (production) plugin("kotlin-dce-js")
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
    maven { setUrl("https://dl.bintray.com/cfraser/muirwik") }
}

val jarsToExtractJs = mutableListOf<String>()
dependencies {
    compile(kotlin("stdlib-js", kotlinVersion))

    compile("org.jetbrains", "kotlin-react", "16.6.0-pre.67-kotlin-$kotlinVersion")
    compile("org.jetbrains", "kotlin-react-dom", "16.6.0-pre.67-kotlin-$kotlinVersion")
    compile("org.jetbrains", "kotlin-styled", "1.0.0-pre.67-kotlin-$kotlinVersion")

    println("Version is $muirwikComponentVersion")

    val muirwik = "com.ccfraser.muirwik:muirwik-components:$muirwikComponentVersion"
    implementation(muirwik)
    jarsToExtractJs.add(muirwik)
}

val compileKotlin2Js: Kotlin2JsCompile by tasks

compileKotlin2Js.kotlinOptions {
    sourceMap = true
    metaInfo = true
    freeCompilerArgs = listOf("-Xcoroutines=enable")
    outputFile = "${project.buildDir.path}/js/app.js"
    main = "call"
    moduleKind = "commonjs"
}

fun addInputsAndOutputs(exec: Exec) {
    // We uncomment this when we want to recreate even if Gradle thinks we don't need to
//    outputs.upToDateWhen { false }
    exec.inputs.file("yarn.lock")
    exec.inputs.file("webpack.config.js")
    exec.inputs.file("webpack.config.prod.js")
    exec.inputs.dir("$buildDir/js")
    exec.inputs.dir("$projectDir/src/main/resources/public")
//    exec.inputs.dir("node_modules")

    exec.outputs.dir("$buildDir/dist")
}

val webpackDev by tasks.creating(Exec::class) {
    group = "webpack"
    description = "Development build"

    addInputsAndOutputs(this)
    commandLine("$projectDir/node_modules/.bin/webpack-cli", "--config", "$projectDir/webpack.config.js")
}

val webpackProd by tasks.creating(Exec::class) {
    group = "webpack"
    description = "Runs webpack-cli with a production config."
    doFirst {
        if (!production) {
            error("Variable production == false and we are doing a production build")
        }
    }

    addInputsAndOutputs(this)
    commandLine("$projectDir/node_modules/.bin/webpack-cli", "-p", "--config", "$projectDir/webpack.config.prod.js")
}

val webpackDevServer by tasks.creating(Exec::class) {
    group = "webpack"
    description = "Starts webpack development server (doesn't open a new browser window)."

    addInputsAndOutputs(this)
    commandLine("$projectDir/node_modules/.bin/webpack-dev-server", "--hot")
}

val webpackDevServerOpenBrowser by tasks.creating(Exec::class) {
    group = "webpack"
    description = "Starts webpack development server and opens a new browser window."

    addInputsAndOutputs(this)
    commandLine("$projectDir/node_modules/.bin/webpack-dev-server", "--hot", "--open")
}

val webpackDevServerPublic by tasks.creating(Exec::class) {
    group = "webpack"
    description = "Starts webpack development server with PCs ip address (rather than localhost) so " +
            "other PCs can browse to it)."

    addInputsAndOutputs(this)
    commandLine("$projectDir/node_modules/.bin/webpack-dev-server", "--hot", "--open", "--host", "0.0.0.0")
}

val webpackDevServerProdConfig by tasks.creating(Exec::class) {
    group = "webpack"
    description = "Though not the usual case, this starts the development server but with a production build."

    addInputsAndOutputs(this)
    commandLine("$projectDir/node_modules/.bin/webpack-dev-server", "-p", "--config", "$projectDir/webpack.config.prod.js")
}

val webpackStats by tasks.creating(Exec::class) {
    group = "webpack"
    description = "Does a webpack build with statics output ready for something like webpack-bundle-analyzer to analyse."

    addInputsAndOutputs(this)
    commandLine("$projectDir/node_modules/.bin/webpack-cli", "-p", "--config", "$projectDir/webpack.config.prod.js", "--profile", "--json")

    standardOutput = ByteArrayOutputStream()
    doLast {
        File("$projectDir/build/stats.prod.json").writeText(standardOutput.toString())
    }
}

val webpackStatsAnalyser by tasks.creating(Exec::class) {
    group = "webpack"
    description = "Assumes that webpack-bundle-analyzer has been installed in npm globally and that the webpackStats task has been run. " +
            "This simply calls the command line with the output of the previous webpackStats task output."

    commandLine("webpack-bundle-analyzer", "$projectDir/build/stats.prod.json", "$projectDir/dist",
            "--mode", "static", "--report", "$projectDir/build/report.html")
}

val copyResources by tasks.creating {
    group = "build"
    description = "Assemble resources part of the web application."

    outputs.dir("$projectDir/dist")

    doLast {
        copy {
            from("$projectDir/src/main/resources/public")
            into("$projectDir/dist")
        }
    }
}

val copyLibJsFiles by tasks.creating {
    group = "build"
    description = "Assemble js parts of the web application taken from jar dependencies."

    inputs.property("compileClasspath", configurations["compileClasspath"])
    inputs.property("testCompileClasspath", configurations["testCompileClasspath"])
    val outputDir = file("$buildDir/web")
    outputs.dir(outputDir)

    doLast {
        val matchedArtifacts = mutableSetOf<ResolvedArtifact>()

        configurations["compileClasspath"].resolvedConfiguration.firstLevelModuleDependencies.forEach {
            matchedArtifacts.addAll(it.allModuleArtifacts.filter { (it.type == "jar") && jarsToExtractJs.contains(it.moduleVersion.toString()) })
        }

        configurations["testCompileClasspath"].resolvedConfiguration.firstLevelModuleDependencies.forEach {
            matchedArtifacts.addAll(it.allModuleArtifacts.filter { (it.type == "jar") && jarsToExtractJs.contains(it.moduleVersion.toString()) })
        }

        matchedArtifacts.forEach {
            copy {
                includeEmptyDirs = false
                from(zipTree(it.file))
                into(outputDir)
                include("**/*.js")
                include("**/*.map")
            }
        }
    }
}

val cleanDist by tasks.creating {
    group = "build"
    description = "Cleans files placed in the dist folder by part of the build process."

    doLast {
        delete("$projectDir/dist")
    }
}

tasks["assemble"].dependsOn(copyResources)
tasks["assemble"].dependsOn(copyLibJsFiles)
tasks["clean"].dependsOn(cleanDist)

if (production) {
    val build by tasks
//    val bundle by tasks
    val webpackDev by tasks
    val webpackProd by tasks
    val webpackDevServerProdConfig by tasks

    build.dependsOn("runDceKotlinJs")
//    bundle.dependsOn("runDceKotlinJs")
    webpackDev.dependsOn("runDceKotlinJs")
    webpackProd.dependsOn("runDceKotlinJs")
    webpackDevServerProdConfig.dependsOn("runDceKotlinJs")
}

