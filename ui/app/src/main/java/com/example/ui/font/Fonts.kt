package com.example.ui.font

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.ui.R

private fun Int.dpToSp(density: Density): TextUnit = with(density) { this@dpToSp.dp.toSp() }
val Int.textDp: TextUnit @Composable get() = this.dpToSp(density = LocalDensity.current)

val notosanskr = FontFamily(
    Font(R.font.notosanskr_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.notosanskr_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.notosanskr_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.notosanskr_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.notosanskr_thin, FontWeight.Thin, FontStyle.Normal)
)
