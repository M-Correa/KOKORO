package com.kokoro.ai.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Wardrobe : Screen("wardrobe")
    object Personality : Screen("personality")
}
