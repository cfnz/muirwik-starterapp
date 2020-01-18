package com.ccfraser.muirwik.starterapp

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.card.*
import com.ccfraser.muirwik.components.transitions.mCollapse
import kotlinx.css.*
import kotlinx.css.properties.*
import react.*
import styled.css
import styled.styledDiv
import kotlin.browser.window

class App : RComponent<RProps, RState>() {
    private var expanded = false

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
                                "It doesn't really do anything except provide a starting point for development.", component = "p")
                        mTypography("For a more featured app, see the test app.", component = "p")
                    }
                }
                mCardActions {
                    css {
                        justifyContent = JustifyContent.spaceBetween
                    }
                    mButton("Learn More", color = MColor.primary, hRefOptions = HRefOptions("https://github.com/cfnz/muirwik"))

                    mIconButton("expand_more", onClick = { setState { expanded = !expanded }}) {
                        css {
                            if (expanded) transform.rotate(180.deg)
                            else transform.rotate(0.deg)

                            transition("transform", 500.ms, Timing.easeInOut)
                        }
                    }
                }
                mCollapse(show = expanded) {
                    mCardContent {
                        mTypography(paragraph = true) {
                            +"""
                                This content is hidden and shown by use of the mCollapse control. It also shows a small demo 
                                of the badge control. You can find more controls in the test app following the 'Learn More' link.
                            """.trimIndent()
                        }
                        testBadges()
                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}



