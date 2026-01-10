package com.projet.mobile.growth.rendu.data

import com.projet.mobile.growth.rendu.data.entity.ExerciseEntity
import com.projet.mobile.growth.rendu.data.entity.TrainingEntity
import com.projet.mobile.growth.rendu.model.Training

fun TrainingEntity.toModel(exercises: List<Exercise>) =
    Training(
        id = id,
        title = title,
        timeEstimation = timeEstimation,
        exercises = exercises
    )

fun Training.toEntity() =
    TrainingEntity(
        id = id,
        title = title,
        timeEstimation = timeEstimation
    )

fun ExerciseEntity.toModel(): Exercise {
    return Exercise(
        name = name,
        gif = gif,
        id = exerciseId,
        repNumber = repNumber,
        setNumber = setNumber,
        weight = weight,
    )
}

fun Exercise.toEntity(trainingId: String): ExerciseEntity {
    return ExerciseEntity(
        trainingId = trainingId,
        exerciseId = id,
        name = name,
        gif = gif,
        repNumber = repNumber,
        setNumber = setNumber,
        weight = weight
    )
}
