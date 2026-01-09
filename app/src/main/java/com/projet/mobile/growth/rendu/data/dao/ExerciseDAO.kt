package com.projet.mobile.growth.rendu.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projet.mobile.growth.rendu.data.entity.ExerciseEntity

@Dao
interface ExerciseDAO {

    @Query("SELECT * FROM exercises WHERE trainingId = :trainingId")
    suspend fun getExercisesForTraining(trainingId: String): List<ExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercises WHERE trainingId = :trainingId")
    suspend fun deleteExercisesForTraining(trainingId: String)
}

