package com.ccfraser.muirwik.starterapp

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.form.mFormControl
import com.ccfraser.muirwik.components.form.mFormLabel
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.px
import react.*
import react.dom.div
import styled.StyleSheet
import styled.css


class TestBadges : RComponent<Props, State>() {
    var anchorOriginHorizontal = MBadgeAnchorOriginHorizontal.left
    var anchorOriginVertical = MBadgeAnchorOriginVertical.top

    private object ComponentStyles : StyleSheet("ComponentStyles", isStatic = true) {
        val margin by css {
            margin(2.spacingUnits)
        }
    }

    override fun RBuilder.render() {
        div {
            mTypography("Badge Component Alignment") {
                css(ComponentStyles.margin)
            }
            mGridContainer {
                css(ComponentStyles.margin)
                mGridItem {
                    mFormControl {
                        mFormLabel("Vertical")
                        mRadioGroup(value = anchorOriginVertical.toString()) {
                            mRadioWithLabel("Top", value = MBadgeAnchorOriginVertical.top.toString(), onChange = {
                                _, checked -> setState { anchorOriginVertical = if (checked) MBadgeAnchorOriginVertical.top else MBadgeAnchorOriginVertical.bottom }
                            })
                            mRadioWithLabel("Bottom", value = MBadgeAnchorOriginVertical.bottom.toString(), onChange = {
                                _, checked -> setState { anchorOriginVertical = if (checked) MBadgeAnchorOriginVertical.bottom else MBadgeAnchorOriginVertical.top }
                            })
                        }
                    }
                }
                mGridItem {
                    mFormControl {
                        mFormLabel("Horizontal")
                        mRadioGroup(value = anchorOriginHorizontal.toString()) {
                            mRadioWithLabel("Left", value = MBadgeAnchorOriginHorizontal.left.toString(), onChange = {
                                _, checked -> setState { anchorOriginHorizontal = if (checked) MBadgeAnchorOriginHorizontal.left else MBadgeAnchorOriginHorizontal.right }
                            })
                            mRadioWithLabel("Right", value = MBadgeAnchorOriginHorizontal.right.toString(), onChange = {
                                _, checked -> setState { anchorOriginHorizontal = if (checked) MBadgeAnchorOriginHorizontal.right else MBadgeAnchorOriginHorizontal.left }
                            })
                        }
                    }
                }
            }
            mBadgeDot(MBadgeColor.primary) {
                css(ComponentStyles.margin)
                attrs.anchorOriginHorizontal = anchorOriginHorizontal
                attrs.anchorOriginVertical = anchorOriginVertical
                mIcon("mail", color = MIconColor.action)
            }
            mBadge(4, color = MBadgeColor.primary) {
                css(ComponentStyles.margin)
                attrs.anchorOriginHorizontal = anchorOriginHorizontal
                attrs.anchorOriginVertical = anchorOriginVertical
                mIcon("mail", color = MIconColor.action)
            }
            mBadge(14, color = MBadgeColor.primary) {
                css(ComponentStyles.margin)
                attrs.anchorOriginHorizontal = anchorOriginHorizontal
                attrs.anchorOriginVertical = anchorOriginVertical
                mIcon("mail", color = MIconColor.action)
            }
        }
    }
}

fun RBuilder.testBadges() = child(TestBadges::class) {}
