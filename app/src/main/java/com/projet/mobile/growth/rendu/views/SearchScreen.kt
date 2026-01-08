package com.projet.mobile.growth.rendu.views

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import com.projet.mobile.growth.rendu.data.Exercise
import com.projet.mobile.growth.rendu.data.ExerciseAPI

@Composable
fun SearchScreen() {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Recherche d'exercice",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Rechercher") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    searchQuery = query
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Rechercher")
                }
            },
        )
        LaunchedEffect(searchQuery) {
            if (searchQuery.isNotEmpty()) {
                val response = ExerciseAPI.exerciseWebService.fetchSearchResults(searchQuery)
                searchResults = response.body()?.data
            } else {
                searchResults = emptyList()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResults?.size ?: 0) {
                Text(text = searchResults?.get(it)?.name ?: "")
                AsyncImage(
                    imageLoader = imageLoader,
                    model = searchResults?.get(it)?.gif,
                    contentDescription = "Exercise GIF",
                )
            }
        }
    }
}
