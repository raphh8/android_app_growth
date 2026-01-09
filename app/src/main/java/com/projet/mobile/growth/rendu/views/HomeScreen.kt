package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    val weeks = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(7) { weekIndex ->
            Card(
                shape = RoundedCornerShape(12.dp),
                //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = weeks[weekIndex],
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(all = 8.dp)
                )
                weekPlan[weekIndex]?.forEach { training ->
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
