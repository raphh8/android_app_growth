package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projet.mobile.growth.rendu.data.Exercise
import com.projet.mobile.growth.rendu.data.ExerciseAPI

@Composable
fun ExerciseDetailsScreen(
    exerciseId: String,
    navController: NavController
) {
    var details by remember { mutableStateOf<Exercise?>(null) }

    LaunchedEffect(exerciseId) {
        val response = ExerciseAPI.exerciseWebService.fetchExerciseDetails(exerciseId)
        details = response.body()?.data
    }

    when {
        details == null -> {
            CircularProgressIndicator()
        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Text(
                    text = "Détails de l'exercice \"" + details!!.name + "\"",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Muscles:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = details!!.muscles?.joinToString(", ") ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Body Parts:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = details!!.bodyParts?.joinToString(", ") ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Equipments:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = details!!.equipments?.joinToString(", ") ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Secondary Muscles:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = details!!.secondaryMuscles?.joinToString(", ") ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Instructions:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = details!!.instructions?.joinToString("\n") ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                FloatingActionButton(
                    onClick = { navController.popBackStack()},
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                }
            }
        }
    }
}