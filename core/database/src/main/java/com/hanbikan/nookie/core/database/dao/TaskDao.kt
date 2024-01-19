package com.hanbikan.nookie.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hanbikan.nookie.core.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE task.user_id == :userId")
    fun getAllTasksByUserId(userId: Int): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTasks(vararg taskEntities: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Query("DELETE FROM task WHERE task.id == :id")
    suspend fun deleteTaskById(id: Int)

    @Query("UPDATE task SET is_done = 0")
    suspend fun resetAllTasks()
}