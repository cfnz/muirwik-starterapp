package com.ccfraser.muirwik.starterapp

import com.ccfraser.muirwik.components.mThemeProvider
import com.ccfraser.muirwik.components.styles.createMuiTheme
import com.ccfraser.muirwik.components.styles.mStylesProvider
import react.dom.render
import react.setState
import kotlin.browser.document

@JsModule("react-hot-loader")
private external val hotModule: dynamic
private val hot = hotModule.hot
private val module = js("module")

fun main(args: Array<String>) {

    val hotWrapper = hot(module)
    render(document.getElementById("root")) {
        mStylesProvider("jss-insertion-point") {
            mThemeProvider {
                hotWrapper(app())
            }
        }
    }
}
