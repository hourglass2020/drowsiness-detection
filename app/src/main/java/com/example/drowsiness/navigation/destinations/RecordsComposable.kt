package com.example.drowsiness.navigation.destinations

import androidx.navigation.NavGraphBuilder
import com.example.drowsiness.ui.viewmodels.SharedViewModel
import com.example.drowsiness.utils.Constants
import androidx.navigation.compose.composable
import com.example.drowsiness.ui.screens.records.RecordsScreen

fun NavGraphBuilder.recordsComposable(
    navigateToHomeScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(route = Constants.RECORDS_SCREEN) {
        RecordsScreen(navigateToHomeScreen = navigateToHomeScreen, sharedViewModel = sharedViewModel)
    }
}