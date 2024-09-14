package com.example.drowsiness.navigation

import androidx.navigation.NavHostController
import com.example.drowsiness.utils.Constants

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "home") {
            popUpTo(Constants.SPLASH_SCREEN) { inclusive = true }
        }
    }

    val home: () -> Unit = {
        navController.navigate(route = "home") {
            popUpTo(Constants.HOME_SCREEN) { inclusive = true }
        }
    }

    val records: () -> Unit = {
        navController.navigate(route = "records") {
            popUpTo(Constants.RECORDS_SCREEN) { inclusive = true }
        }
    }

    val drowsy: () -> Unit = {
        navController.navigate(route = "drowsy") {
            popUpTo(Constants.DROWSINESS_SCREEN) { inclusive = true }
        }
    }
}