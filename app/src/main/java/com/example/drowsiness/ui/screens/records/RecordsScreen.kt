package com.example.drowsiness.ui.screens.records

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drowsiness.ui.viewmodels.SharedViewModel

@Composable
fun RecordsScreen(
    navigateToHomeScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllRecords()
    }

    val allRecords by sharedViewModel.allRecords.collectAsState()

    Scaffold(
        topBar = {
            RecordsAppBar(navigateToHomeScreen = navigateToHomeScreen)
        },
        content = { paddingValue ->
            LazyColumn(modifier = Modifier.padding(paddingValue)) {
                items(items = allRecords,
                    key = { record ->
                        record.id
                    }) { record ->
                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                            .fillParentMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(10)
                            )
                            .padding(all = 16.dp)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = record.date,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsAppBar(navigateToHomeScreen: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "History of Alarms", color = MaterialTheme.colorScheme.onPrimary)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = { navigateToHomeScreen() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}