package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ChronoScreen() {
    var inputSeconds by remember { mutableStateOf("30") }
    var durationMillis by remember { mutableStateOf(30_000L) }
    var currentTime by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf(1f) }

    LaunchedEffect(isRunning, isPaused) {
        while (isRunning && !isPaused && currentTime > 0) {
            delay(100L)
            currentTime -= 100L
            value = currentTime.toFloat() / durationMillis.toFloat()
        }
        if (currentTime <= 0L) {
            isRunning = false
            isPaused = false
            value = 0f
        }
        if(!isRunning) {
            value = 0f
            currentTime = 0
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Minuteur",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = inputSeconds,
            onValueChange = { inputSeconds = it.filter { c -> c.isDigit() } },
            label = { Text("Durée (secondes)") },
            enabled = !isRunning,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(contentAlignment = Alignment.Center) {
            val primaryColor = MaterialTheme.colorScheme.primary
            val secondaryColor = MaterialTheme.colorScheme.secondary
            val outlineColor = MaterialTheme.colorScheme.outline

            Canvas(modifier = Modifier.size(280.dp)) {
                val stroke = 14.dp.toPx()
                val radius = size.minDimension / 2
                val center = Offset(size.width / 2, size.height / 2)

                drawArc(
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    style = Stroke(stroke, cap = StrokeCap.Round),
                    color = outlineColor
                )

                drawArc(
                    startAngle = -215f,
                    sweepAngle = 250f * value,
                    useCenter = false,
                    style = Stroke(stroke, cap = StrokeCap.Round),
                    color = primaryColor
                )

                val beta = (250f * value + 145f) * (PI / 180f).toFloat()
                val a = cos(beta) * radius
                val b = sin(beta) * radius

                drawPoints(
                    listOf(Offset(center.x + a, center.y + b)),
                    pointMode = PointMode.Points,
                    strokeWidth = stroke * 1.5f,
                    cap = StrokeCap.Round,
                    color = secondaryColor
                )
            }

            Text(
                text = "${currentTime / 1000}s",
                fontSize = 42.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (!isRunning) {
                    val seconds = inputSeconds.toLongOrNull() ?: 0L
                    if (seconds > 0) {
                        durationMillis = seconds * 1000
                        currentTime = durationMillis
                        value = 1f
                        isRunning = true
                    }
                } else {
                    isRunning = false
                    isPaused = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = if (isRunning) "Stop" else "Go",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if(isRunning) {
            Button(
                onClick = {
                    isPaused = !isPaused
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = if (isPaused) "Go" else "Pause",
                    fontSize = 20.sp
                )
            }
        }
    }
}
