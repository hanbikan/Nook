package com.hanbikan.nook.feature.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.GetActiveUserIdUseCase
import com.hanbikan.nook.core.domain.usecase.GetTutorialDayUseCase
import com.hanbikan.nook.core.domain.usecase.GetTutorialTasksByUserIdAndDayUseCase
import com.hanbikan.nook.core.domain.usecase.GetUserByIdUseCase
import com.hanbikan.nook.core.domain.usecase.SetLastVisitedRouteUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateTutorialTaskUseCase
import com.hanbikan.nook.feature.tutorial.navigation.tutorialScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val getTutorialTasksByUserIdAndDayUseCase: GetTutorialTasksByUserIdAndDayUseCase,
    private val updateTutorialTaskUseCase: UpdateTutorialTaskUseCase,
    setLastVisitedRouteUseCase: SetLastVisitedRouteUseCase,
    getActiveUserIdUseCase: GetActiveUserIdUseCase,
    getUserByIdUseCase: GetUserByIdUseCase,
    getTutorialDayUseCase: GetTutorialDayUseCase,
) : ViewModel() {

    // Data for UI
    val tutorialDay: StateFlow<Int?> = getTutorialDayUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val activeUserId: StateFlow<Int?> = getActiveUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeUser: StateFlow<User?> = activeUserId
        .flatMapLatest { getUserByIdUseCase(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val tutorialTaskList: StateFlow<List<TutorialTask>> = activeUserId
        .combine(tutorialDay) { activeUserId, tutorialDay ->
            if (activeUserId == null || tutorialDay == null) {
                flowOf(listOf())
            } else {
                getTutorialTasksByUserIdAndDayUseCase(activeUserId, tutorialDay)
            }
        }
        .flatMapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    // Ui state
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TutorialUiState> = tutorialTaskList.mapLatest {
        if (it.isEmpty()) {
            TutorialUiState.Loading
        } else {
            TutorialUiState.Success
        }
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TutorialUiState.Loading)

    // Dialog
    private val _isUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserDialogShown = _isUserDialogShown.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            setLastVisitedRouteUseCase(tutorialScreenRoute)
        }
    }

    fun switchUserDialog() {
        _isUserDialogShown.value = !isUserDialogShown.value
    }

    fun switchTutorialTask(index: Int) {
        val target = tutorialTaskList.value.getOrNull(index) ?: return
        val newTutorialTask = target.copy(isDone = !target.isDone)
        viewModelScope.launch(Dispatchers.IO) {
            updateTutorialTaskUseCase(newTutorialTask)
        }
    }
}

sealed interface TutorialUiState {
    object Loading : TutorialUiState
    object Success : TutorialUiState
}