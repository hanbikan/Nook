package com.hanbikan.nooknook.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hanbikan.nooknook.core.database.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>

    @Insert
    fun insertTasks(vararg tasks: Task)

    @Query("DELETE FROM task WHERE task.id == :id")
    fun deleteTaskById(id: Int)
}