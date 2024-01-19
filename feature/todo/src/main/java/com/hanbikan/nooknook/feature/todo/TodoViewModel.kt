package com.hanbikan.nooknook.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nooknook.core.domain.model.Task
import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.usecase.AddTaskUseCase
import com.hanbikan.nooknook.core.domain.usecase.DeleteTaskUseCase
import com.hanbikan.nooknook.core.domain.usecase.GetActiveUserIdUseCase
import com.hanbikan.nooknook.core.domain.usecase.GetAllTasksByUserIdUseCase
import com.hanbikan.nooknook.core.domain.usecase.GetUserByIdUseCase
import com.hanbikan.nooknook.core.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    getAllTasksByUserIdUseCase: GetAllTasksByUserIdUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getActiveUserIdUseCase: GetActiveUserIdUseCase,
    getUserByIdUseCase: GetUserByIdUseCase,
) : ViewModel() {
    // Ui state
    private val _uiState: MutableStateFlow<TodoUiState> = MutableStateFlow(TodoUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // Data for UI
    private val activeUserId: StateFlow<Int?> = getActiveUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeUser: StateFlow<User?> = activeUserId
        .flatMapLatest { getUserByIdUseCase(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val taskList: StateFlow<List<Task>> = activeUserId
        .flatMapLatest { getAllTasksByUserIdUseCase(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    val doneTaskCount = taskList.mapLatest { taskList ->
        taskList.count { it.isDone }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    // Dialog
    private val _isAddTaskDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAddTaskDialogShown = _isAddTaskDialogShown.asStateFlow()

    private val _isDeleteTaskDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDeleteTaskDialogShown = _isDeleteTaskDialogShown.asStateFlow()

    private val _isUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserDialogShown = _isUserDialogShown.asStateFlow()

    // Long-click 시 제거할 작업을 잠시 여기에 저장한 뒤, 삭제 버튼을 누를 때 이 값을 참조하여 제거합니다.
    private val _taskIdToDelete: MutableStateFlow<Int?> = MutableStateFlow(null)
    val taskIdToDelete = _taskIdToDelete.asStateFlow()

    init {
        // Fake loading for UX
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            updateSuccessUiState()
            taskList.collectLatest {
                updateSuccessUiState()
            }
        }
    }

    fun addTask(name: String) {
        if (name.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            activeUserId.value?.let {
                val task = Task(0, it, name, false)
                addTaskUseCase(task)
                switchAddTaskDialog()
            }
        }
    }

    fun onConfirmDeleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            taskIdToDelete.value?.let {
                deleteTaskUseCase(it)
                switchDeleteTaskDialog()
            }
        }
    }

    fun switchTask(index: Int) {
        val target = taskList.value.getOrNull(index) ?: return
        val newTask = target.copy(isDone = !target.isDone)
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskUseCase(newTask)
        }
    }

    fun onLongClickTask(task: Task) {
        _taskIdToDelete.value = task.id
        switchDeleteTaskDialog()
    }

    fun switchAddTaskDialog() {
        _isAddTaskDialogShown.value = !isAddTaskDialogShown.value
    }

    fun switchDeleteTaskDialog() {
        _isDeleteTaskDialogShown.value = !isDeleteTaskDialogShown.value
    }

    fun switchUserDialog() {
        _isUserDialogShown.value = !isUserDialogShown.value
    }

    private fun updateSuccessUiState() {
        _uiState.value =
            if (taskList.value.isEmpty()) TodoUiState.Success.Empty else TodoUiState.Success.NotEmpty
    }
}

sealed interface TodoUiState {
    object Loading : TodoUiState
    sealed interface Success : TodoUiState {
        object Empty : Success
        object NotEmpty : Success
    }
}