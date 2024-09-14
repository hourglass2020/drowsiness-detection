package com.example.drowsiness.ui.screens.drowsy

import android.content.Context
import android.graphics.PointF
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drowsiness.R
import com.example.drowsiness.analyzers.FaceDetectionAnalyzer
import com.example.drowsiness.components.CameraView
import com.example.drowsiness.ui.viewmodels.SharedViewModel
import com.example.drowsiness.utils.adjustPoint
import com.example.drowsiness.utils.adjustSize
import com.example.drowsiness.utils.drawBounds
import com.example.drowsiness.utils.drawLandmark
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.delay
import java.util.Date

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DrowsyScreen(
    navigateToHomeScreen: () -> Unit, sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current
    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    PermissionRequired(permissionState = cameraPermissionState, permissionNotGrantedContent = {
        LaunchedEffect(Unit) {
            cameraPermissionState.launchPermissionRequest()
        }
    }, permissionNotAvailableContent = {
        Column {
            Toast.makeText(context, "Permission denied.", Toast.LENGTH_LONG).show()
        }
    }, content = {
        ScanSurface(navigateToHomeScreen = navigateToHomeScreen, sharedViewModel=sharedViewModel)
    })
}

@Composable
fun ScanSurface(navigateToHomeScreen: () -> Unit, sharedViewModel: SharedViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val faces = remember { mutableStateListOf<Face>() }

    var isDrowsy by remember { mutableStateOf(false) }
    var isClosedEyes by remember { mutableStateOf(false) }

    val screenWidth = remember { mutableStateOf(context.resources.displayMetrics.widthPixels) }
    val screenHeight = remember { mutableStateOf(context.resources.displayMetrics.heightPixels) }

    val imageWidth = remember { mutableStateOf(0) }
    val imageHeight = remember { mutableStateOf(0) }

    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    LaunchedEffect(key1 = isClosedEyes) {
        delay(3000)
        if (isClosedEyes) {
            isDrowsy = true

            val effect = VibrationEffect.createWaveform(longArrayOf(0, 500, 300), 1)
            vibrator.vibrate(effect)
            sharedViewModel.addRecord(Date().toString())
        } else {
            isDrowsy = false
            vibrator.cancel()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 50.dp)) {
        CameraView(context = context,
            lifecycleOwner = lifecycleOwner,
            analyzer = FaceDetectionAnalyzer { meshes, width, height ->
                faces.clear()
                faces.addAll(meshes)
                imageWidth.value = width
                imageHeight.value = height
            })

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navigateToHomeScreen() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White
                    )
                }
                Text(
                    text = stringResource(id = R.string.face_detection_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDrowsy) Color.White else MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (isDrowsy) "You are drowsy!" else "",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }
        DrawFaces(
            onFindDrowsiness = { status -> isClosedEyes = status },
            faces = faces,
            imageHeight.value,
            imageWidth.value,
            screenWidth.value,
            screenHeight.value
        )
    }
}


@Composable
fun DrawFaces(
    onFindDrowsiness: (Boolean) -> Unit,
    faces: List<Face>,
    imageWidth: Int,
    imageHeight: Int,
    screenWidth: Int,
    screenHeight: Int
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        faces.forEach { face ->
            val boundingBox = face.boundingBox.toComposeRect()
            val topLeft = adjustPoint(
                PointF(boundingBox.topLeft.x, boundingBox.topLeft.y),
                imageWidth,
                imageHeight,
                screenWidth,
                screenHeight
            )
            val size =
                adjustSize(boundingBox.size, imageWidth, imageHeight, screenWidth, screenHeight)
            drawBounds(topLeft, size, Color.Yellow, 5f)

            if (getDrowsinessProbability(face)) {
                onFindDrowsiness(true)
            } else {
                onFindDrowsiness(false)
            }

            face.allLandmarks.forEach {
                val landmark = adjustPoint(
                    PointF(it.position.x, it.position.y),
                    imageWidth,
                    imageHeight,
                    screenWidth,
                    screenHeight
                )
                drawLandmark(landmark, Color.Cyan, 3f)
            }

        }
    }

}

fun getDrowsinessProbability(face: Face): Boolean {
    if (face.leftEyeOpenProbability != null) {
        if (face.leftEyeOpenProbability!! < 0.3f || face.rightEyeOpenProbability!! < 0.3f) {
            return true
        } else {
            return false
        }
    }
    return false
}