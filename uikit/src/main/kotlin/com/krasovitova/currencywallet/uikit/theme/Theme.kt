package com.krasovitova.currencywallet.uikit.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalColors = staticCompositionLocalOf { LightColors }
private val LocalTypography = staticCompositionLocalOf { DefaultTypography }

object CurrencyWalletTheme {

    val colors: CurrencyWalletColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: CurrencyWalletTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Composable
fun CurrencyWalletTheme(content: @Composable () -> Unit) {
    val colors = LightColors

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides DefaultTypography,
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                primary = colors.primary,
                onPrimary = colors.onPrimary,
                primaryContainer = colors.primaryContainer,
                onPrimaryContainer = colors.onPrimaryContainer,
                secondary = colors.secondary,
                onSecondary = colors.onSecondary,
                background = colors.background,
                surface = colors.surface,
                onSurface = colors.onSurface,
                onSurfaceVariant = colors.onSurfaceVariant,
                error = colors.error,
                onError = colors.onError,
            ),
            content = content,
        )
    }
}
