package com.projet.mobile.growth.tp2and3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.projet.mobile.growth.MainActivity
import com.projet.mobile.growth.data.api
import com.projet.mobile.growth.list.Task
import com.projet.mobile.growth.ui.theme.GrowthTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.util.Map.entry
import java.util.UUID
import kotlin.collections.plus

@Serializable
data object ListNavScreen : NavKey
@Serializable
data class DetailNavScreen(val task: Task) : NavKey

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrowthTheme {
                App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    // on créé notre historique de navigation avec la liste comme écran initial
    val backStack = rememberNavBackStack(ListNavScreen)
    val context = LocalContext.current
    val vm: TaskListViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    var userName by remember { mutableStateOf("Loading user...") }

                    LaunchedEffect(Unit) {
                        try {
                            val response = api.userWebService.fetchUser()
                            if (response.isSuccessful) {
                                userName = response.body()?.name ?: "User name not found"
                            } else {
                                userName = "Error: ${response.code()}"
                            }
                        } catch (e: Exception) {
                            userName = "Error: ${e.message}"
                        }
                    }
                    Text("To Do List of $userName")
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "go to classic app"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavDisplay(
                backStack = backStack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                entryProvider = entryProvider {
                    entry<ListNavScreen> {
                        ListScreen(
                            vm = vm,
                            modifier = Modifier,
                            onClickItem = { task -> backStack.add(DetailNavScreen(task)) },
                            onAddTask = { task -> vm.add(task) },
                            onDeleteTask = { task -> vm.remove(task) }
                        )
                    }
                    entry<DetailNavScreen> { key ->
                        DetailsScreen(
                            task = key.task,
                            onEditTask = { task -> vm.edit(task) },
                            onReturn = { backStack.removeLastOrNull() }
                        )
                    }
                }
            )
        }
    }
}

