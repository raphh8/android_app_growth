package com.projet.mobile.growth.rendu.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.projet.mobile.growth.rendu.model.Training
import com.projet.mobile.growth.rendu.navigation.AddTraining

@Composable
fun TrainingsScreen(
    list: SnapshotStateList<Training>,
    navController: NavController,
    onDelete: (trainingToDel: Training) -> Unit,
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AddTraining)
            })
            {
                Icon(Icons.Default.Add, contentDescription = "Add new training")
            }
        }
    ) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding)) {
        items(count = list.toList().count()){
            index->
            Column {
                Row (modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.secondary).padding(10.dp)){
                    Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(fontSize = 20.sp, text = list.toList()[index].title )
                        Spacer(Modifier.height(10.dp))
                        Text(fontSize = 20.sp, text = list.toList()[index].timeEstimation)
                    }
                    IconButton(onClick = {
                        onDelete(list.toList()[index])
                    }) {
                        Icon(imageVector = Icons.Default.Delete,
                            contentDescription = "Delete")
                    }
                } }
        }
    }

}
}