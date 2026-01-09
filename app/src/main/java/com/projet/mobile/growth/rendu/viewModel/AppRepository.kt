package com.projet.mobile.growth.rendu.viewModel

import com.projet.mobile.growth.rendu.data.AppDB
import com.projet.mobile.growth.rendu.data.entity.WeeklyPlanEntity
import com.projet.mobile.growth.rendu.data.toEntity
import com.projet.mobile.growth.rendu.data.toModel
import com.projet.mobile.growth.rendu.model.Training
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class AppRepository(
    private val db: AppDB
) {

    fun getTrainings(): Flow<List<Training>> {
        return db.trainingDao()
            .getAllTrainings()
            .map { trainingEntities ->
                trainingEntities.map { entity ->
                    val exercises = db.exerciseDao()
                        .getExercisesForTraining(entity.id)
                        .map { it.toModel() }

                    entity.toModel(exercises)
                }
            }
    }

    suspend fun addTraining(training: Training) {
        db.trainingDao().insertTraining(training.toEntity())

        db.exerciseDao().insertExercises(
            training.exercises.map { it.toEntity(training.id) }
        )
    }

    suspend fun deleteTraining(training: Training) {
        db.trainingDao().deleteTraining(training.toEntity())
        db.weeklyPlanDao().deleteTrainingFromWeek(training.id)
    }

    suspend fun getWeeklyPlan(): Map<Int, List<Training>> {
        val result = mutableMapOf<Int, List<Training>>()

        (0..6).forEach { day ->
            val trainingIds = db.weeklyPlanDao()
                .getTrainingsForDay(day)
                .map { it.trainingId }

            val trainingsForDay = trainingIds.mapNotNull { id ->
                db.trainingDao().getTrainingById(id)?.let { entity ->
                    val exercises = db.exerciseDao()
                        .getExercisesForTraining(id)
                        .map { it.toModel() }

                    entity.toModel(exercises)
                }
            }

            result[day] = trainingsForDay
        }

        return result
    }

    suspend fun addTrainingsToDay(trainings: List<Training>, day: Int) {
        db.weeklyPlanDao().insertWeeklyPlan(
            trainings.map {
                WeeklyPlanEntity(day = day, trainingId = it.id)
            }
        )
    }

    suspend fun removeTrainingFromDay(training: Training, day: Int) {
        db.weeklyPlanDao().removeTrainingFromDay(training.id, day)
    }
}
