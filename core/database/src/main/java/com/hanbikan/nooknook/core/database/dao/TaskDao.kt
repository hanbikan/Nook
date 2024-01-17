package com.hanbikan.nooknook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hanbikan.nooknook.core.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTasks(vararg taskEntities: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Query("DELETE FROM task WHERE task.id == :id")
    suspend fun deleteTaskById(id: Int)

    @Query("UPDATE task SET is_done = 0")
    suspend fun resetAllTasks()
}