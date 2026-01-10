package com.projet.mobile.growth.rendu.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projet.mobile.growth.rendu.data.dao.ExerciseDAO
import com.projet.mobile.growth.rendu.data.dao.TrainingDAO
import com.projet.mobile.growth.rendu.data.dao.WeeklyPlanDAO
import com.projet.mobile.growth.rendu.data.entity.ExerciseEntity
import com.projet.mobile.growth.rendu.data.entity.TrainingEntity
import com.projet.mobile.growth.rendu.data.entity.WeeklyPlanEntity

@Database(
    entities = [
        TrainingEntity::class,
        ExerciseEntity::class,
        WeeklyPlanEntity::class
    ],
    version = 1
)
abstract class AppDB : RoomDatabase() {

    abstract fun trainingDao(): TrainingDAO
    abstract fun exerciseDao(): ExerciseDAO
    abstract fun weeklyPlanDao(): WeeklyPlanDAO

    companion object {
        @Volatile private var INSTANCE: AppDB? = null

        fun getDatabase(context: Context): AppDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "growth_app_db"
                )
                .build().also { INSTANCE = it }
            }
    }
}
