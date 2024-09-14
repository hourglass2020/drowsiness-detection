package com.example.drowsiness.navigation.destinations

import androidx.navigation.NavGraphBuilder
import com.example.drowsiness.ui.screens.home.HomeScreen
import com.example.drowsiness.ui.viewmodels.SharedViewModel
import com.example.drowsiness.utils.Constants
import androidx.navigation.compose.composable

fun NavGraphBuilder.homeComposable(
    navigateToDrowsyScreen: () -> Unit,
    navigateToRecordsScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(route = Constants.HOME_SCREEN) {
        HomeScreen(
            navigateToRecordsScreen = navigateToRecordsScreen,
            navigateToDrowsyScreen = navigateToDrowsyScreen,
            sharedViewModel = sharedViewModel
        )
    }
}