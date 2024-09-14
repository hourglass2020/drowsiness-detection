package com.example.drowsiness.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.drowsiness.R
import com.example.drowsiness.components.ImageCard
import com.example.drowsiness.ui.viewmodels.SharedViewModel

@Composable
fun HomeScreen(
    navigateToDrowsyScreen: () -> Unit,
    navigateToRecordsScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    Scaffold(
        topBar = { HomeAppBar() },
        content = {
            LazyColumn(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = it
            ) {
                item {
                    ImageCard(
                        title = stringResource(id = R.string.face_detection_title),
                        description = stringResource(id = R.string.face_detection_description),
                        imageUrl = painterResource(id = R.drawable.face_detection2x),
                        onCardClick = { navigateToDrowsyScreen() }
                    )
                }

                item {
                    ImageCard(
                        title = stringResource(id = R.string.text_recognition_title),
                        description = stringResource(id = R.string.text_recognition_description),
                        imageUrl = painterResource(id = R.drawable.text_recognition2x),
                        onCardClick = { navigateToRecordsScreen() }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar() {
    TopAppBar(
        title = {
            Text(text = "Drowsiness Detection", color = MaterialTheme.colorScheme.onPrimary)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        actions = {}
    )
}
