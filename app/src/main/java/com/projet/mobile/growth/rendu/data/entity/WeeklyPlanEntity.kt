package com.projet.mobile.growth.rendu.data.entity

import androidx.room.Entity

@Entity(
    tableName = "weekly_plan",
    primaryKeys = ["day", "trainingId"]
)
data class WeeklyPlanEntity(
    val day: Int,
    val trainingId: String
)
