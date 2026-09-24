package com.kokoro.ai.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Pastel & Neon Palette
val KokoroViolet = Color(0xFF8B5CF6)
val KokoroVioletLight = Color(0xFFA78BFA)
val KokoroVioletDark = Color(0xFF7C3AED)

val KokoroPink = Color(0xFFEC4899)
val KokoroPinkLight = Color(0xFFF472B6)
val KokoroPinkDark = Color(0xFFDB2777)

// Backgrounds & Glassmorphism
val KokoroBackground = Color(0xFFFAF5FF)
val KokoroSurface = Color(0xFFFFFFFF)
val KokoroCardBackground = Color(0xEBFFFFFF)
val KokoroGlassBorder = Color(0x80FFFFFF)
val KokoroGlassTint = Color(0x338B5CF6)

// Text Colors
val KokoroTextPrimary = Color(0xFF1F1538)
val KokoroTextSecondary = Color(0xFF6B5E87)
val KokoroTextHint = Color(0xFF9D92B3)

// VIP Gold & Accents
val KokoroGold = Color(0xFFF59E0B)
val KokoroGoldLight = Color(0xFFFBBF24)
val KokoroGreen = Color(0xFF10B981)
val SpotifyGreen = Color(0xFF1DB954)
val PedidosYaPink = Color(0xFFEC4899)

// Gradients
val KokoroGradientPrimary = Brush.horizontalGradient(
    colors = listOf(KokoroViolet, KokoroPink)
)

val KokoroGradientVip = Brush.horizontalGradient(
    colors = listOf(Color(0xFFF59E0B), Color(0xFFEC4899), Color(0xFF8B5CF6))
)

val KokoroGradientBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF3E8FF),
        Color(0xFFFAF5FF),
        Color(0xFFFDF2F8)
    )
)
