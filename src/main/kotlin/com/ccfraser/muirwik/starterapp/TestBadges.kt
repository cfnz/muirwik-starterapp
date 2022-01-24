package com.ccfraser.muirwik.starterapp

import com.ccfraser.muirwik.components.*
import kotlinx.css.margin
import react.*
import react.dom.div
import styled.StyleSheet
import styled.css


class TestBadges : RComponent<Props, State>() {
    var anchorOriginHorizontal = BadgeAnchorOriginHorizontal.left
    var anchorOriginVertical = BadgeAnchorOriginVertical.top

    private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val margin by css {
            margin(2.spacingUnits)
        }
    }

    override fun RBuilder.render() {
        div {
            typography("Badge Component Alignment") {
                css(ComponentStyles.margin)
            }
            gridContainer {
                css(ComponentStyles.margin)
                gridItem {
                    formControl {
                        formLabel("Vertical")
                        radioGroup(value = anchorOriginVertical.toString()) {
                            radioWithLabel("Top", value = BadgeAnchorOriginVertical.top.toString()) {
                                attrs.onChange = { _, checked -> setState {
                                    anchorOriginVertical = if (checked) BadgeAnchorOriginVertical.top else BadgeAnchorOriginVertical.bottom
                                } }
                            }
                            radioWithLabel("Bottom", value = BadgeAnchorOriginVertical.bottom.toString()) {
                                attrs.onChange = { _, checked -> setState {
                                    anchorOriginVertical = if (checked) BadgeAnchorOriginVertical.bottom else BadgeAnchorOriginVertical.top
                                } }
                            }
                        }
                    }
                }
                gridItem {
                    formControl {
                        formLabel("Horizontal")
                        radioGroup(value = anchorOriginHorizontal.toString()) {
                            radioWithLabel("Left", value = BadgeAnchorOriginHorizontal.left.toString()) {
                                attrs.onChange = { _, checked -> setState {
                                    anchorOriginHorizontal = if (checked) BadgeAnchorOriginHorizontal.left else BadgeAnchorOriginHorizontal.right
                                } }
                            }
                            radioWithLabel("Right", value = BadgeAnchorOriginHorizontal.right.toString()) {
                                attrs.onChange = { _, checked -> setState {
                                    anchorOriginHorizontal = if (checked) BadgeAnchorOriginHorizontal.right else BadgeAnchorOriginHorizontal.left
                                } }
                            }
                        }
                    }
                }
            }
            badgeDot(BadgeColor.primary) {
                css(ComponentStyles.margin)
                attrs.anchorOriginHorizontal = anchorOriginHorizontal
                attrs.anchorOriginVertical = anchorOriginVertical
                icon("mail", color = IconColor.action)
            }
            badge(4, color = BadgeColor.primary) {
                css(ComponentStyles.margin)
                attrs.anchorOriginHorizontal = anchorOriginHorizontal
                attrs.anchorOriginVertical = anchorOriginVertical
                icon("mail", color = IconColor.action)
            }
            badge(14, color = BadgeColor.primary) {
                css(ComponentStyles.margin)
                attrs.anchorOriginHorizontal = anchorOriginHorizontal
                attrs.anchorOriginVertical = anchorOriginVertical
                icon("mail", color = IconColor.action)
            }
        }
    }
}

fun RBuilder.testBadges() = child(TestBadges::class) {}
