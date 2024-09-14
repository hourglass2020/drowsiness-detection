package com.example.drowsiness.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.example.drowsiness.navigation.destinations.drowsyComposable
import com.example.drowsiness.navigation.destinations.homeComposable
import com.example.drowsiness.navigation.destinations.recordsComposable
import com.example.drowsiness.navigation.destinations.splashComposable
import com.example.drowsiness.ui.viewmodels.SharedViewModel
import com.example.drowsiness.utils.Constants

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(navController = navController, startDestination = Constants.SPLASH_SCREEN) {
        splashComposable(navigateToHomeScreen = screen.splash)
        homeComposable(
            navigateToDrowsyScreen = screen.drowsy,
            navigateToRecordsScreen = screen.records,
            sharedViewModel = sharedViewModel
        )
        recordsComposable(navigateToHomeScreen = screen.home, sharedViewModel = sharedViewModel)
        drowsyComposable(navigateToHomeScreen = screen.home, sharedViewModel = sharedViewModel)
    }
}