package com.kokoro.ai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kokoro.ai.ui.screens.HomeScreen
import com.kokoro.ai.ui.screens.PersonalityScreen
import com.kokoro.ai.ui.screens.WardrobeScreen
import com.kokoro.ai.ui.viewmodel.KokoroViewModel

@Composable
fun KokoroNavGraph(
    navController: NavHostController,
    viewModel: KokoroViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToWardrobe = {
                    navController.navigate(Screen.Wardrobe.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Wardrobe.route) {
            WardrobeScreen(viewModel = viewModel)
        }

        composable(Screen.Personality.route) {
            PersonalityScreen(viewModel = viewModel)
        }
    }
}
