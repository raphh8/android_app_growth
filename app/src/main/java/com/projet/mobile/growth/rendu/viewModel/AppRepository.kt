package com.projet.mobile.growth.rendu.viewModel

import com.projet.mobile.growth.rendu.model.Training

class AppRepository {
    private var trainingList = mutableListOf<Training>()
    private val weeklyPlan: MutableMap<Int, MutableList<Training>> = (0..6).associateWith { mutableListOf<Training>() }.toMutableMap()



    fun getTrainingList(): List<Training> {
        return trainingList
    }

    fun addTraining(training: Training) {
        trainingList.add(training)
    }

    fun deleteTraining(training: Training) {
        trainingList.remove(training)
        weeklyPlan.values.forEach { it.remove(training) }
    }

    fun getWeeklyPlan(): Map<Int, List<Training>> =
        weeklyPlan.mapValues { it.value.toList() }

    fun getTrainingsForDay(day: Int): List<Training> =
        weeklyPlan[day]?.toList().orEmpty()

    fun addTrainingToDay(training: Training, day: Int) {
        weeklyPlan[day]?.add(training)
    }

    fun removeTrainingFromDay(training: Training, day: Int) {
        weeklyPlan[day]?.remove(training)
    }

}