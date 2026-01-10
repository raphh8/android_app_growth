package com.projet.mobile.growth.rendu.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercises",
    foreignKeys = [
        ForeignKey(
            entity = TrainingEntity::class,
            parentColumns = ["id"],
            childColumns = ["trainingId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val trainingId: String,
    val exerciseId: String,
    val name: String,
    val gif: String,
    val repNumber: Int?,
    val setNumber: Int?,
    val weight: Int?
)
