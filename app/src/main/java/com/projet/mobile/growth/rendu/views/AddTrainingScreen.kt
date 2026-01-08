package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projet.mobile.growth.rendu.data.Exercise
import com.projet.mobile.growth.rendu.model.Training
import java.util.UUID


@Composable
fun AddTrainingScreen(
    navController: NavController,
    onSave: (Training) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var timeEstimation by remember { mutableStateOf("") }
    var exercises by remember { mutableStateOf(emptyList<Exercise>()) }

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

        Button(onClick = {  }) {
            Text("Ajouter un exercice")
        }

        Button(
            onClick = {
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