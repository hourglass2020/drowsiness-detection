package com.example.drowsiness.ui.screens.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.drowsiness.R
import com.example.drowsiness.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToHomeScreen: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 1000)
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f, animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(Constants.SPLASH_SCREEN_DELAY)
        navigateToHomeScreen()
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_alert_bell),
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
                .offset(y = offsetState)
                .alpha(alpha = alphaState)
        )
        Text(
            text = "Drowsiness Detection",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}
