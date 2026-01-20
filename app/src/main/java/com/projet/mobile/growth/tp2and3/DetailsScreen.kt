package com.projet.mobile.growth.tp2and3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projet.mobile.growth.list.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    task: Task,
    onEditTask: (Task) -> Unit,
    onReturn: () -> Unit,
    ) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Title: ${task.title}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Description: ${task.description}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        FloatingActionButton(
            onClick = { onReturn() }
        ) {
            Text("OK")
        }
        FloatingActionButton(
            onClick = {
                showDialog = true
            }
        ) {
            Text("Edit")
        }
    }
    if(showDialog) EditDialog(
        onDismiss = { showDialog = false },
        onConfirm = { newTitle, newDesc ->
            task.title = newTitle
            task.description = newDesc
            onEditTask(task)
            showDialog = false
        })
}

@Composable
fun EditDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var newTitle by remember { mutableStateOf("") }
    var newDesc by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Modification")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = newTitle,
                    onValueChange = { newTitle = it },
                    label = { Text("New Title") },
                )
                OutlinedTextField(
                    value = newDesc,
                    onValueChange = { newDesc = it },
                    label = { Text("New Description") }
                )

            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(newTitle, newDesc)
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


