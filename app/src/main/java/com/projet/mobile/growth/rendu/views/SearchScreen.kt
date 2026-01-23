package com.projet.mobile.growth.rendu.views

import com.projet.mobile.growth.R
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.projet.mobile.growth.rendu.data.Exercise
import com.projet.mobile.growth.rendu.data.ExerciseAPI
import com.projet.mobile.growth.rendu.model.BodyParts
import com.projet.mobile.growth.rendu.model.Equipment
import com.projet.mobile.growth.rendu.model.Muscles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun bodyPartIcon(bodyPart: BodyParts): Painter =
    painterResource(
        id = when (bodyPart) {
            BodyParts.NECK -> R.drawable.neck
            BodyParts.SHOULDERS -> R.drawable.shoulders
            BodyParts.UPPER_ARMS -> R.drawable.upper_arm
            BodyParts.LOWER_ARMS -> R.drawable.lower_arm
            BodyParts.CHEST -> R.drawable.chest
            BodyParts.BACK -> R.drawable.back
            BodyParts.WAIST -> R.drawable.waist
            BodyParts.UPPER_LEGS -> R.drawable.upper_leg
            BodyParts.LOWER_LEGS -> R.drawable.lower_leg
            BodyParts.CARDIO -> R.drawable.cardio
            BodyParts.NULL -> R.drawable.baseline_info_outline_24
        }
    )

@Composable
fun SearchScreen(
    onExerciseClick: (Exercise) -> Unit
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    var query by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Exercise>?>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }

    var filteredSearch by rememberSaveable { mutableStateOf(false) }
    var selectedBodyParts by remember { mutableStateOf(setOf<BodyParts>()) }
    var selectedMuscles by remember { mutableStateOf(setOf<Muscles>()) }
    var selectedEquipment by remember { mutableStateOf(setOf<Equipment>()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Recherche d'exercice",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Rechercher") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(
                    onClick = { searchQuery = query.trim() },
                    enabled = query.isNotBlank()) {
                    Icon(Icons.Default.Search, contentDescription = "Rechercher")
                }
            },
        )
        Spacer(Modifier.height(8.dp))
        FilledTonalButton(onClick = { filteredSearch = !filteredSearch }) {
            Text(if (filteredSearch) "Masquer les filtres" else "Filtres avancés")
        }

        AnimatedVisibility(
            visible = filteredSearch,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Filtres", style = MaterialTheme.typography.titleMedium)

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Parties du corps",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(BodyParts.entries.filter { it != BodyParts.NULL }) { bodyPart ->
                            val isSelected = bodyPart in selectedBodyParts
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedBodyParts =
                                        if (isSelected) selectedBodyParts - bodyPart
                                        else selectedBodyParts + bodyPart
                                },
                                label = { Text(bodyPart.displayName) },
                                leadingIcon = {
                                    Icon(
                                        painter = bodyPartIcon(bodyPart),
                                        contentDescription = "body part icon",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            )
                        }
                    }
                    Text(
                        "Équipements",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(Equipment.entries.filter { it != Equipment.NULL }) { equipment ->
                            val isSelected = equipment in selectedEquipment
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedEquipment =
                                        if (isSelected) selectedEquipment - equipment
                                        else selectedEquipment + equipment
                                },
                                label = { Text(equipment.displayName) },
                            )
                        }
                    }
                    Text(
                        "Muscles",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(Modifier.height(4.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(Muscles.entries.filter { it != Muscles.NULL }) { muscle ->
                            val isSelected = muscle in selectedMuscles
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedMuscles =
                                        if (isSelected) selectedMuscles - muscle
                                        else selectedMuscles + muscle
                                },
                                label = { Text(muscle.displayName) },
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { selectedBodyParts = emptySet() }) {
                            Text("Réinitialiser")
                        }
                    }
                }
            }
        }

        LaunchedEffect(searchQuery, selectedBodyParts, selectedMuscles) {
            if (searchQuery.isBlank()) return@LaunchedEffect
            delay(500)
            val response =
                if (selectedBodyParts.isEmpty() && selectedMuscles.isEmpty()) {
                    ExerciseAPI.exerciseWebService
                        .fetchSearchResults(searchQuery)
                } else {
                    ExerciseAPI.exerciseWebService
                        .fetchExercisesFiltered(
                            searchQuery,
                            selectedBodyParts.joinToString(",") { it.displayName },
                            selectedMuscles.joinToString(",") { it.displayName },
                        )
                }
            if (response.code() == 429) {
                Log.w("SearchScreen", "Rate limit hit (429)")
                return@LaunchedEffect
            }
            searchResults = response.body()?.data ?: emptyList()
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResults ?: emptyList()) { exercise ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onExerciseClick(exercise)
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = exercise.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        AsyncImage(
                            imageLoader = imageLoader,
                            model = exercise.gif,
                            contentDescription = "Exercise GIF",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                    }
                }
            }
        }
    }
}