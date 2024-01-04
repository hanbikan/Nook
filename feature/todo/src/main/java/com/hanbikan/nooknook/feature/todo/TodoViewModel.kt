package com.hanbikan.nooknook.feature.todo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel: ViewModel() {
    val userName: String = "Isabelle"

    private val _taskList: MutableStateFlow<List<Task>> = MutableStateFlow(listOf(
        Task(false, "Daily meeting"),
        Task(false, "Daily meeting"),
        Task(true, "Daily meeting"),
        Task(false, "Daily meeting"),
        Task(false, "Daily meeting"),
        Task(false, "Daily meeting"),
    ))
    val taskList = _taskList.asStateFlow()

    fun addTask(task: Task) {
        _taskList.value = taskList.value + task
    }

    fun switchTask(index: Int) {
        _taskList.value = taskList.value.also {
            it[index].isDone != it[index].isDone
        }
    }
}

// TODO: Move this model to other package
data class Task(
    val isDone: Boolean,
    val name: String,
)