package com.projet.mobile.growth.tp2and3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import java.util.UUID
import com.projet.mobile.growth.list.Task

@Composable
fun ListScreen(
    vm: TaskListViewModel,
    modifier: Modifier = Modifier,
    onClickItem: (Task) -> Unit,
    onAddTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val state = vm.tasksStateFlow.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            val currentState = state.value
            if (currentState is TaskListState.Success) {
                FloatingActionButton(
                    onClick = {
                        val newTask = Task(
                            id = UUID.randomUUID().toString(),
                            title = "Item #${currentState.list.size}"
                        )
                        onAddTask(newTask)
                        coroutineScope.launch {
                            listState.animateScrollToItem(currentState.list.size-1)
                        }
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    ) { paddingValues ->
        when (val currentState = state.value) {
            is TaskListState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is TaskListState.Error -> {
                Text(
                    text = currentState.message,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is TaskListState.Success -> {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(currentState.list) { task ->
                        TaskItem(
                            item = task,
                            onClick = { onClickItem(task) },
                            onDelete = { onDeleteTask(task) }
                        )
                    }
                }
            }
        }
    }
}
