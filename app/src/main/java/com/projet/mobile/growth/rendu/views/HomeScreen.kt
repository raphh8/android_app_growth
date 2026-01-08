package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.projet.mobile.growth.rendu.model.Training
import com.projet.mobile.growth.rendu.navigation.AddActivity
import com.projet.mobile.growth.rendu.navigation.AddTraining
import java.util.UUID
import kotlin.collections.plus

@Composable
fun HomeScreen(
    weekPlan : Map<Int, List<Training>>,
    navController: NavController
) {
    val weeks = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(7) {
            Text( text = weeks[it], modifier = Modifier.padding(8.dp) )
            FloatingActionButton(
                onClick = {
                    navController.navigate(AddActivity(day = it))
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically )
                {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    Text(text = "Ajouter une activité physique")
                }
            }
            weekPlan[it]?.forEach { training ->
                Text(training.title)
            }
        }
    }
}
