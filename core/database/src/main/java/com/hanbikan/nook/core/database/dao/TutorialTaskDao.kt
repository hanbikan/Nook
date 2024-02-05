package com.hanbikan.nook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hanbikan.nook.core.database.entity.TutorialTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TutorialTaskDao {
    @Query("SELECT * FROM tutorial_task WHERE tutorial_task.user_id == :userId")
    fun getTasksByUserId(userId: Int): Flow<List<TutorialTaskEntity>>

    @Query("SELECT * FROM tutorial_task WHERE tutorial_task.user_id == :userId and tutorial_task.day == :day")
    fun getTasksByUserIdAndDay(userId: Int, day: Int): Flow<List<TutorialTaskEntity>>

    @Insert
    suspend fun insertTutorialTasks(vararg tutorialTaskEntities: TutorialTaskEntity)

    @Update
    suspend fun updateTutorialTask(tutorialTaskEntity: TutorialTaskEntity)
}