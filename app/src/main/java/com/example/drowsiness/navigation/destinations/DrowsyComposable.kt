package com.example.drowsiness.navigation.destinations

import androidx.navigation.NavGraphBuilder
import com.example.drowsiness.utils.Constants
import androidx.navigation.compose.composable
import com.example.drowsiness.ui.screens.drowsy.DrowsyScreen
import com.example.drowsiness.ui.viewmodels.SharedViewModel

fun NavGraphBuilder.drowsyComposable(
    navigateToHomeScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(route = Constants.DROWSINESS_SCREEN) {
        DrowsyScreen(navigateToHomeScreen = navigateToHomeScreen, sharedViewModel = sharedViewModel)
    }
}