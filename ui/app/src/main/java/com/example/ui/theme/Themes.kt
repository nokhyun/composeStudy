package com.example.ui.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object GrayRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = Color.Gray
    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(1.0f, 1.0f, 1.0f, 1.0f)
}