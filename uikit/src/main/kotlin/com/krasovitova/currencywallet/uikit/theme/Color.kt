package com.krasovitova.currencywallet.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CurrencyWalletColors(
    val primary: Color,
    val primaryDark: Color,
    val primaryContainer: Color,
    val onPrimary: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val error: Color,
    val onError: Color,
    val income: Color,
    val expense: Color,
)

internal val LightColors = CurrencyWalletColors(
    primary = Color(0xFF6C5CE7),
    primaryDark = Color(0xFF5B4FCF),
    primaryContainer = Color(0xFFEDE9FE),
    onPrimary = Color.White,
    onPrimaryContainer = Color(0xFF3D1A8A),
    secondary = Color(0xFF00B894),
    onSecondary = Color.White,
    background = Color(0xFFF8F7FF),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    onSurfaceVariant = Color(0xFF49454F),
    error = Color(0xFFE17055),
    onError = Color.White,
    income = Color(0xFF00B894),
    expense = Color(0xFFE17055),
)
