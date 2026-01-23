package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projet.mobile.growth.rendu.model.Training
import com.projet.mobile.growth.rendu.navigation.AddTraining

@Composable
fun TrainingsScreen(
    list: List<Training>,
    navController: NavController,
    onDelete: (trainingToDel: Training) -> Unit,
) {
    val expandedMap = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AddTraining)
            })
            {
                Icon(Icons.Default.Add, contentDescription = "Add new training")
            }
        }
    ) { innerPadding ->
        Text(
            text = "Mes séances",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(10.dp))
        LazyColumn(Modifier.padding(innerPadding)) {
            items(count = list.count()) { index ->
                val isExpanded = expandedMap[list[index].id] ?: false
                Column(
                    modifier = Modifier
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier) {
                                Text(text = list[index].title, style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = list[index].exercises.size.toString() + " exercices - " + list[index].timeEstimation,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            IconButton(
                                modifier = Modifier,
                                onClick = {
                                    onDelete(list[index])
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                        FilledTonalButton(
                            modifier = Modifier.padding(4.dp),
                            onClick = {
                                expandedMap[list[index].id] = !isExpanded
                            }
                        ) {
                            Text(if(isExpanded) "Cacher" else "Voir plus")
                        }
                        if (isExpanded) {
                            ExerciseCarousel(list[index].exercises)
                        }
                    }
                }
            }
        }
    }
}