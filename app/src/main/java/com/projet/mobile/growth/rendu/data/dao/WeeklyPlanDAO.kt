package com.projet.mobile.growth.rendu.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projet.mobile.growth.rendu.data.entity.WeeklyPlanEntity

@Dao
interface WeeklyPlanDAO {
    @Query("SELECT * FROM weekly_plan WHERE day = :day")
    suspend fun getTrainingsForDay(day: Int): List<WeeklyPlanEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeeklyPlan(entries: List<WeeklyPlanEntity>)

    @Query("DELETE FROM weekly_plan WHERE trainingId = :trainingId")
    suspend fun deleteTrainingFromWeek(trainingId: String)

    @Query("DELETE FROM weekly_plan WHERE trainingId = :trainingId AND day = :day")
    suspend fun removeTrainingFromDay(trainingId: String, day: Int)
}

