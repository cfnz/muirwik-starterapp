package com.ccfraser.muirwik.starterapp

import com.ccfraser.muirwik.components.styles.mStylesProvider
import com.ccfraser.muirwik.components.themeProvider
import react.dom.render
import kotlinx.browser.document

@JsModule("react-hot-loader")
private external val hotModule: dynamic
private val hot = hotModule.hot
private val module = js("module")

fun main() {
    val hotWrapper = hot(module)
    render(document.getElementById("root")!!) {
        mStylesProvider("jss-insertion-point") {
            themeProvider {
                hotWrapper(app())
            }
        }
    }
}
