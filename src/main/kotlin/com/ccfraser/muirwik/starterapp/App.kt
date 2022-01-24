package com.ccfraser.muirwik.starterapp

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.utils.HRefOptions
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.*
import react.*
import styled.css
import styled.styledDiv

class App : RComponent<Props, State>() {
    private var expanded = false

    override fun RBuilder.render() {
        mCssBaseline()

        styledDiv {
            css {
                padding(16.px)
            }

            card {
                css {
                    width = 440.px
                    maxWidth = 100.pct
                }
                cardActionArea {
                    attrs.onClick = { window.alert("You clicked the action area.") }
                    cardMedia(image = "/images/muirwik-logo.png") {
                        attrs.title = "Muirwik"
                        css { height = 200.px }
                    }
                    cardContent {
                        typography("Muirwik Starter App", TypographyVariant.h5) {
                            attrs.gutterBottom = true
                        }
                        typography("This is a starter app for Muirwik - A Material UI React wrapper written in Kotlin. " +
                                "It doesn't really do much except provide a starting point for development.")
                        typography("This card has a collapsed area, use the right up/down arrow to show more.") {
                            attrs.gutterBottom = true
                        }
                        typography("For a more featured app, see the test app")
                    }
                }
                cardActions {
                    css {
                        justifyContent = JustifyContent.spaceBetween
                        paddingLeft = 16.px
                    }
                    link("Learn More", HRefOptions("https://github.com/cfnz/muirwik"))

                    iconButton("expand_more") {
                        attrs.onClick = { setState { expanded = !expanded } }
                        css {
                            if (expanded) transform.rotate(180.deg)
                            else transform.rotate(0.deg)

                            transition("transform", 500.ms, Timing.easeInOut)
                        }
                    }
                }
                collapse(show = expanded) {
                    cardContent {
                        typography("""
                                This content is hidden and shown by use of the collapse control. It also shows a small demo 
                                of the badge control. You can find more controls in the test app following the 'Learn More' link.
                            """.trimIndent())
                        testBadges()
                    }
                }
            }
        }
    }
}

fun RBuilder.app() {
    child(App::class) {}
}



