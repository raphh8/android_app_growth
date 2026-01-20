package com.projet.mobile.growth.tp2and3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projet.mobile.growth.data.api
import com.projet.mobile.growth.list.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class TaskListState {
    data object Loading : TaskListState()
    data class Success(val list: List<Task>) : TaskListState()
    data class Error(val message: String) : TaskListState()
}
class TaskListViewModel : ViewModel() {
    private val webService = api.taskWebService

    public val tasksStateFlow = MutableStateFlow<TaskListState>(TaskListState.Loading)

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            tasksStateFlow.value = TaskListState.Loading
            try {
                val response = webService.fetchTasks() // Call HTTP (opération longue)
                if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                    tasksStateFlow.value = TaskListState.Error("Error: ${response.raw()}")
                    return@launch
                }

                tasksStateFlow.value = TaskListState.Success(response.body().orEmpty())
            } catch (e: Exception) {
                tasksStateFlow.value = TaskListState.Error("Error: ${e.message}")
            }
        }
    }

    // à compléter plus tard:
    fun add(task: Task) {
        viewModelScope.launch {
            val response = webService.create(task)
            if (!response.isSuccessful) {
                tasksStateFlow.value = TaskListState.Error("Error: ${response.raw()}")
                return@launch
            }

            val newTask = response.body()!!
            val currentState = tasksStateFlow.value
            if (currentState is TaskListState.Success) {
                tasksStateFlow.value = TaskListState.Success(currentState.list + newTask)
            }
        }
    }

    fun edit(task: Task) {
        viewModelScope.launch {
            val response = webService.update(task)
            if (!response.isSuccessful) {
                tasksStateFlow.value = TaskListState.Error("Error: ${response.raw()}")
                return@launch
            }

            val updatedTask = response.body()!!
            val currentState = tasksStateFlow.value
            if (currentState is TaskListState.Success) {
                tasksStateFlow.value = TaskListState.Success(currentState.list.map {
                    if (it.id == updatedTask.id) updatedTask else it
                })
            }
        }
    }

    fun remove(task: Task) {
        viewModelScope.launch {
            val response = webService.delete(task.id)
            if (!response.isSuccessful) {
                tasksStateFlow.value = TaskListState.Error("Error: ${response.raw()}")
                return@launch
            }

            val currentState = tasksStateFlow.value
            if (currentState is TaskListState.Success) {
                tasksStateFlow.value = TaskListState.Success(currentState.list - task)
            }
        }
    }
}