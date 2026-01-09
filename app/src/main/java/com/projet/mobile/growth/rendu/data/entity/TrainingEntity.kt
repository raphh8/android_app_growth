package com.projet.mobile.growth.rendu.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings")
data class TrainingEntity(
    @PrimaryKey val id: String,
    val title: String,
    val timeEstimation: String
)