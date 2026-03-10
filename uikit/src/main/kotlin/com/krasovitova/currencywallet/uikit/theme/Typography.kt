package com.krasovitova.currencywallet.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CurrencyWalletTypography(
    val displayLarge: TextStyle,
    val headingLarge: TextStyle,
    val headingMedium: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val bigButton: TextStyle, //TODO добавить medium and small button style
)

internal val DefaultTypography = CurrencyWalletTypography(
    displayLarge = TextStyle(
        fontSize = 44.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
    ),
    headingLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    headingMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp,
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
    ),
    bigButton = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.5.sp,
    )
)
