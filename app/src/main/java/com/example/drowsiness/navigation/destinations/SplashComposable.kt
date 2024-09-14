package com.example.drowsiness.navigation.destinations

import androidx.navigation.NavGraphBuilder
import com.example.drowsiness.utils.Constants
import androidx.navigation.compose.composable
import com.example.drowsiness.ui.screens.splash.SplashScreen

fun NavGraphBuilder.splashComposable(
    navigateToHomeScreen: () -> Unit,
) {
    composable(route = Constants.SPLASH_SCREEN) {
        SplashScreen(navigateToHomeScreen = navigateToHomeScreen)
    }
}