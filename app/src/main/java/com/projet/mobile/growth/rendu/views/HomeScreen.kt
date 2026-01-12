package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projet.mobile.growth.rendu.model.Training
import com.projet.mobile.growth.rendu.navigation.AddActivity

@Composable
fun HomeScreen(
    weekPlan : Map<Int, List<Training>>,
    navController: NavController,
    onDel: (Training, Int) -> Unit
) {
    val expandedMap = remember { mutableStateMapOf<String, Boolean>() }
    val weeks = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(weeks) { weekIndex, weekName ->
            Card(
                shape = RoundedCornerShape(12.dp),
                //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = weekName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(all = 8.dp)
                )
                weekPlan[weekIndex]?.forEach { training ->
                    val isExpanded = expandedMap[training.id] ?: false

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
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = training.title,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Text(
                                    text = training.timeEstimation,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                            IconButton(
                                onClick = { onDel(training, weekIndex) }
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
                                expandedMap[training.id] = !isExpanded
                            }
                        ) {
                            Text(if(isExpanded) "Cacher" else "Voir plus")
                        }
                        if (isExpanded) {
                            ExerciseCarousel(training.exercises)
                        }
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate(AddActivity(day = weekIndex)) },
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ajouter une activité",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
