package com.projet.mobile.growth.rendu.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.projet.mobile.growth.rendu.model.Training

class TrainingViewModel : ViewModel() {

    var repo = AppRepository()

    val trainings = mutableStateListOf<Training>()
    val weeklyPlan = mutableStateMapOf<Int, SnapshotStateList<Training>>()

    init {
        trainings.addAll(repo.getTrainingList())

        repo.getWeeklyPlan().forEach { (day, list) ->
            weeklyPlan[day] = mutableStateListOf<Training>().apply {
                addAll(list)
            }
        }
    }

    fun addNewTraining(training: Training) {
        repo.addTraining(training)
        trainings.add(training)
    }

    fun deleteTraining(training: Training) {
        repo.deleteTraining(training)
        trainings.remove(training)
        weeklyPlan.values.forEach { it.remove(training) }
    }

    fun addTrainingToDay(training: List<Training>, day: Int) {
        training.forEach {
            repo.addTrainingToDay(it, day)
            weeklyPlan[day]?.add(it)
        }
    }

    fun removeTrainingFromDay(training: Training, day: Int) {
        repo.removeTrainingFromDay(training, day)
        weeklyPlan[day]?.remove(training)
    }
}
