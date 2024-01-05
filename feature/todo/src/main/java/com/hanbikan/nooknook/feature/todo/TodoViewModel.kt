package com.hanbikan.nooknook.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nooknook.core.domain.model.Task
import com.hanbikan.nooknook.core.domain.usecase.AddTaskUseCase
import com.hanbikan.nooknook.core.domain.usecase.DeleteTaskUseCase
import com.hanbikan.nooknook.core.domain.usecase.GetAllTasksUseCase
import com.hanbikan.nooknook.core.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    getAllTasksUseCase: GetAllTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {
    private val _userName: MutableStateFlow<String> = MutableStateFlow("Isabelle")
    val userName = _userName.asStateFlow()

    val taskList: StateFlow<List<Task>> = getAllTasksUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    val doneTaskCount = taskList.mapLatest { taskList ->
        taskList.count { it.isDone }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    fun addTask(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = Task(0, name, false)
            addTaskUseCase(task)
        }
    }

    fun switchTask(index: Int) {
        val target = taskList.value.getOrNull(index) ?: return
        val newTask = target.copy(isDone = !target.isDone)
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskUseCase(newTask)
        }
    }
}