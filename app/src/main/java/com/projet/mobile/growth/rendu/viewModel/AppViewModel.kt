package com.projet.mobile.growth.rendu.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.projet.mobile.growth.rendu.data.AppDB
import com.projet.mobile.growth.rendu.model.Training
import com.projet.mobile.growth.rendu.notifications.NotificationScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = AppRepository(
        AppDB.getDatabase(application)
    )

    val trainings: StateFlow<List<Training>> =
        repository.getTrainings()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    private val _weeklyPlan =
        MutableStateFlow<Map<Int, List<Training>>>(emptyMap())

    val weeklyPlan: StateFlow<Map<Int, List<Training>>> =
        _weeklyPlan.asStateFlow()

    init {
        viewModelScope.launch {
            _weeklyPlan.value = repository.getWeeklyPlan()
        }
    }

    fun addNewTraining(training: Training) {
        viewModelScope.launch {
            repository.addTraining(training)
        }
    }

    fun deleteTraining(training: Training) {
        viewModelScope.launch {
            repository.deleteTraining(training)
        }
    }

    fun addTrainingsToDay(trainings: List<Training>, day: Int) {
        viewModelScope.launch {
            repository.addTrainingsToDay(trainings, day)
            _weeklyPlan.value = repository.getWeeklyPlan()
            NotificationScheduler.scheduleNotifications(getApplication(), _weeklyPlan.value)
        }
    }

    fun removeTrainingFromDay(training: Training, day: Int) {
        viewModelScope.launch {
            repository.removeTrainingFromDay(training, day)
            _weeklyPlan.value = repository.getWeeklyPlan()
            NotificationScheduler.scheduleNotifications(getApplication(), _weeklyPlan.value)
        }
    }
}


