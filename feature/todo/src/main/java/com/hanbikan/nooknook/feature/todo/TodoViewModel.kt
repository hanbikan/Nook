package com.hanbikan.nooknook.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class TodoViewModel : ViewModel() {
    private val _userName: MutableStateFlow<String> = MutableStateFlow("Isabelle")
    val userName = _userName.asStateFlow()

    private val _taskList: MutableStateFlow<List<Task>> = MutableStateFlow(
        listOf(
            Task(false, "Daily meeting"),
            Task(false, "Daily meeting"),
            Task(true, "Daily meeting"),
            Task(false, "Daily meeting"),
            Task(false, "Daily meeting"),
            Task(false, "Daily meeting"),
        )
    )
    val taskList = _taskList.asStateFlow()

    val doneTaskCount = taskList.mapLatest { taskList ->
        taskList.count { it.isDone }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    fun addTask(task: Task) {
        _taskList.value = taskList.value + task
    }

    fun switchTask(index: Int) {
        val newTaskList = taskList.value.toMutableList()
        newTaskList[index] = newTaskList[index].copy(isDone = !taskList.value[index].isDone)
        _taskList.value = newTaskList
    }
}

// TODO: Move this model to other package
data class Task(
    val isDone: Boolean,
    val name: String,
)