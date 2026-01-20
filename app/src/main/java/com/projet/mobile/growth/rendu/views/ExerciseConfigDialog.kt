package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.projet.mobile.growth.rendu.model.ActivityType

fun String.onlyPositiveInt(): String =
    filter { it.isDigit() }.trimStart('0')

fun String.onlyPositiveFloat(): String =
    filter { it.isDigit() || it == '.' }
        .let {
            if (it.count { c -> c == '.' } > 1) it.dropLast(1) else it
        }

@Composable
fun ActivitySelector(
    selected: ActivityType,
    onSelect: (ActivityType) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ActivityType.values().forEach { type ->
            FilterChip(
                selected = selected == type,
                onClick = { onSelect(type) },
                label = {
                    Text(type.name.lowercase().replaceFirstChar { it.uppercase() })
                }
            )
        }
    }
}

@Composable
fun ActivityInputs(
    activityType: ActivityType,
    reps: String,
    onRepsChange: (String) -> Unit,
    sets: String,
    onSetsChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    duration: String,
    onDurationChange: (String) -> Unit,
    distance: String,
    onDistanceChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        when (activityType) {

            ActivityType.EXERCISE -> {
                OutlinedTextField(
                    value = reps,
                    onValueChange = { onRepsChange(it.onlyPositiveInt()) },
                    label = { Text("Répétitions") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                OutlinedTextField(
                    value = sets,
                    onValueChange = { onSetsChange(it.onlyPositiveInt()) },
                    label = { Text("Séries") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { onWeightChange(it.onlyPositiveFloat()) },
                    label = { Text("Poids (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            ActivityType.CARDIO -> {
                OutlinedTextField(
                    value = duration,
                    onValueChange = { onDurationChange(it.onlyPositiveInt()) },
                    label = { Text("Durée (minutes)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                OutlinedTextField(
                    value = distance,
                    onValueChange = { onDistanceChange(it.onlyPositiveFloat()) },
                    label = { Text("Distance (km)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            ActivityType.STRETCHING -> {
                OutlinedTextField(
                    value = duration,
                    onValueChange = { onDurationChange(it.onlyPositiveInt()) },
                    label = { Text("Durée (secondes)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                OutlinedTextField(
                    value = reps,
                    onValueChange = { onRepsChange(it.onlyPositiveInt()) },
                    label = { Text("Répétitions") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }
    }
}

@Composable
fun ExerciseConfigDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (reps: Int, sets: Int, weight: Int, duration: Int, distance: Float) -> Unit
) {
    var activityType by remember { mutableStateOf(ActivityType.EXERCISE) }

    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ActivitySelector(
                    selected = activityType,
                    onSelect = { activityType = it }
                )

                ActivityInputs(
                    activityType = activityType,
                    reps = reps,
                    onRepsChange = { reps = it },
                    sets = sets,
                    onSetsChange = { sets = it },
                    weight = weight,
                    onWeightChange = { weight = it },
                    duration = duration,
                    onDurationChange = { duration = it },
                    distance = distance,
                    onDistanceChange = { distance = it }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        reps.toIntOrNull() ?: 0,
                        sets.toIntOrNull() ?: 0,
                        weight.toIntOrNull() ?: 0,
                        duration.toIntOrNull() ?: 0,
                        distance.toFloatOrNull() ?: 0f

                    )
                }
            ) {
                Text("Valider")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
