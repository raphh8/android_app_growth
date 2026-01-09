package com.projet.mobile.growth.rendu.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projet.mobile.growth.rendu.data.entity.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDAO {

    @Query("SELECT * FROM trainings")
    fun getAllTrainings(): Flow<List<TrainingEntity>>

    @Query("SELECT * FROM trainings WHERE id = :id LIMIT 1")
    suspend fun getTrainingById(id: String): TrainingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTraining(training: TrainingEntity)

    @Delete
    suspend fun deleteTraining(training: TrainingEntity)
}
