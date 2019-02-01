package com.ccfraser.muirwik.starterapp

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.card.*
import com.ccfraser.muirwik.components.styles.ThemeOptions
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.px
import kotlinx.css.vw
import react.*
import styled.css
import styled.styledDiv
import kotlin.browser.window

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        mCssBaseline()

        styledDiv {
            css {
                padding(16.px)
            }

            mCard {
                css {
                    width = 440.px
                    maxWidth = 100.pct
                }
                mCardActionArea(onClick = { window.alert("You clicked the action area.") }) {
                    mCardMedia(image = "/images/muirwik-logo.png",
                            title = "Muirwik") {
                        css { height = 200.px }
                    }
                    mCardContent {
                        mTypography("Muirwik Starter App", gutterBottom = true, variant = MTypographyVariant.h5, component = "h2")
                        mTypography("This is a starter app for Muirwik - A Material UI React wrapper written in Kotlin. " +
                                "It doesn't really do anything except provide a starting point for development. " +
                                "For a more featured app, see the test app.", component = "p")
                    }
                }
                mCardActions {
                    mButton("Learn More", true, size = MButtonSize.small,
                            href = "https://github.com/cfnz/muirwik") {
                        // Might add target as a typed prop, but for now we need to use asDynamic
                        attrs.asDynamic().target = "_Blank"
                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}



