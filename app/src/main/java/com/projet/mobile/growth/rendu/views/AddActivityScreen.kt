package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projet.mobile.growth.rendu.model.Training

@Composable
fun AddActivityScreen(
    list : List<Training>,
    navController: NavController,
    onSave: (List<Training>) -> Unit
) {
    var activities by remember { mutableStateOf(emptyList<Training>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Quelle(s) activité(s) souhaitez vous ajouter à votre journée ?")

        list.forEach { training ->
            var checked by remember { mutableStateOf(false)}
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(training.title)
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it }
                )
            }
            if(checked) {
                activities = activities + training
            }
        }

        Button(
            onClick = {
                onSave(activities)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Valider")
        }
    }
}