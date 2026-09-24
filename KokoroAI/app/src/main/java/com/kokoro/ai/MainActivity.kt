package com.kokoro.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kokoro.ai.ui.components.KokoroBottomNavBar
import com.kokoro.ai.ui.navigation.Screen
import com.kokoro.ai.ui.screens.HomeScreen
import com.kokoro.ai.ui.screens.PersonalityScreen
import com.kokoro.ai.ui.screens.WardrobeScreen
import com.kokoro.ai.ui.theme.KokoroGradientBackground
import com.kokoro.ai.ui.theme.KokoroTheme
import com.kokoro.ai.ui.viewmodel.KokoroViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KokoroTheme {
                val viewModel: KokoroViewModel = viewModel()
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()

                // HorizontalPager allows swiping between the 3 screens seamlessly
                val pagerState = rememberPagerState(pageCount = { 3 })

                val snackbarEvent by viewModel.snackbarEvent.collectAsState()
                LaunchedEffect(snackbarEvent) {
                    snackbarEvent?.let { event ->
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.actionLabel,
                            duration = SnackbarDuration.Short
                        )
                        viewModel.clearSnackbar()
                    }
                }

                // If user is not on Home (page 0) and presses system back button, return to Home
                BackHandler(enabled = pagerState.currentPage != 0) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }

                // Map page index to Screen route for bottom navigation bar
                val currentRoute = when (pagerState.currentPage) {
                    0 -> Screen.Home.route
                    1 -> Screen.Wardrobe.route
                    2 -> Screen.Personality.route
                    else -> Screen.Home.route
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(KokoroGradientBackground)
                ) {
                    Scaffold(
                        containerColor = Color.Transparent,
                        snackbarHost = {
                            SnackbarHost(
                                hostState = snackbarHostState,
                                modifier = Modifier.padding(bottom = 80.dp)
                            )
                        },
                        bottomBar = {
                            KokoroBottomNavBar(
                                currentRoute = currentRoute,
                                onNavigateToRoute = { route ->
                                    val targetPage = when (route) {
                                        Screen.Home.route -> 0
                                        Screen.Wardrobe.route -> 1
                                        Screen.Personality.route -> 2
                                        else -> 0
                                    }
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(targetPage)
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) { page ->
                            when (page) {
                                0 -> HomeScreen(
                                    viewModel = viewModel,
                                    onNavigateToWardrobe = {
                                        // Navigate to Wardrobe page smoothly
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(1)
                                        }
                                    }
                                )
                                1 -> WardrobeScreen(viewModel = viewModel)
                                2 -> PersonalityScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
