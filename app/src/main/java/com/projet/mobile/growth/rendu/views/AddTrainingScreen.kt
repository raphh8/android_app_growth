package com.projet.mobile.growth.rendu.views

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.projet.mobile.growth.rendu.data.Exercise
import com.projet.mobile.growth.rendu.model.Training
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrainingScreen(
    navController: NavController,
    onSave: (Training) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var timeEstimation by remember { mutableStateOf("") }
    var exercises by remember { mutableStateOf(emptyList<Exercise>()) }

    val sheetState = rememberModalBottomSheetState()
    var showExerciseBottomSheet by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {title = it},
            label = { Text("Nom de la séance") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            showExerciseBottomSheet = true
        }) {
            Text("Ajouter un exercice")
        }

        if (showExerciseBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showExerciseBottomSheet = false
                },
                sheetState = sheetState
            ) {
                SearchScreen(
                    onExerciseClick = {
                        selectedExercise = it
                        showDialog = true
                    }
                )
            }
        }

        if (showDialog && selectedExercise != null) {
            ExerciseConfigDialog(
                title = selectedExercise!!.name,
                onDismiss = { showDialog = false },
                onConfirm = { reps, sets, weight ->
                    selectedExercise!!.repNumber = reps
                    selectedExercise!!.setNumber = sets
                    selectedExercise!!.weight = weight
                    exercises = exercises + selectedExercise!!
                    showDialog = false
                    showExerciseBottomSheet = false
                }
            )
        }
        exercises.forEach { exercise ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    imageLoader = imageLoader,
                    model = exercise.gif,
                    contentDescription = "Exercise GIF",
                    modifier = Modifier
                )
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(exercise.name)
                    Text(exercise.setNumber.toString() + " * " + exercise.repNumber.toString() + " à " + exercise.weight.toString() + "kg")
                }
                IconButton(
                    onClick = {
                        exercises = exercises - exercise
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }

        Button(
            onClick = {
                if (exercises.isEmpty()) timeEstimation = "Environ 0 minutes"
                else {
                    var totalTime = 0.0
                    exercises.forEachIndexed { index, exercise ->
                        val reps = exercise.repNumber!!
                        val sets = exercise.setNumber!!
                        val restTimePerSet = when {
                            reps <= 6  -> 3.0
                            reps <= 12 -> 2.0
                            reps <= 20 -> 1.5
                            else -> 1.0
                        }
                        val executionTimePerSet = (reps * 5) / 60.0

                        totalTime += sets * executionTimePerSet
                        totalTime += (sets - 1) * restTimePerSet

                        if (index < exercises.lastIndex) {
                            totalTime += 3
                        }
                    }
                    if(totalTime <= 5) timeEstimation = "Moins de 5 minutes"
                    else  {
                        totalTime -= (totalTime % 5)
                        timeEstimation = "Environ ${totalTime.toInt()} minutes"
                    }

                }

                val newTraining = Training(UUID.randomUUID().toString(), title, timeEstimation, exercises)
                onSave(newTraining)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Valider")
        }
    }
}