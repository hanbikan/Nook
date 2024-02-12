package com.hanbikan.nook.feature.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.AddTaskUseCase
import com.hanbikan.nook.core.domain.usecase.DeleteTaskUseCase
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import com.hanbikan.nook.core.domain.usecase.GetAllTasksByUserIdUseCase
import com.hanbikan.nook.core.domain.usecase.SetLastVisitedRouteUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateTaskUseCase
import com.hanbikan.nook.feature.todo.navigation.todoScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    setLastVisitedRouteUseCase: SetLastVisitedRouteUseCase,
    getAllTasksByUserIdUseCase: GetAllTasksByUserIdUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getActiveUserUseCase: GetActiveUserUseCase,
) : ViewModel() {

    // Ui state
    private val _uiState: MutableStateFlow<TodoUiState> = MutableStateFlow(TodoUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // Data for UI
    val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val taskList: StateFlow<List<Task>> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                getAllTasksByUserIdUseCase(it.id).onEach { taskList ->
                    _uiState.value =
                        if (taskList.isEmpty()) TodoUiState.Success.Empty else TodoUiState.Success.NotEmpty
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

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
        viewModelScope.launch(Dispatchers.IO) {
            setLastVisitedRouteUseCase(todoScreenRoute)
        }
    }

    fun addTask(name: String) {
        if (name.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            activeUser.value?.let {
                val task = Task(userId = it.id, name = name, isDaily = true, isDone = false) // TODO: isDaily
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
}

sealed interface TodoUiState {
    object Loading : TodoUiState
    sealed interface Success : TodoUiState {
        object Empty : Success
        object NotEmpty : Success
    }
}