package com.hanbikan.nook.feature.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.common.executeIfBothNonNull
import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import com.hanbikan.nook.core.domain.usecase.GetTutorialDayRangeUseCase
import com.hanbikan.nook.core.domain.usecase.GetTutorialTasksByUserIdAndDayUseCase
import com.hanbikan.nook.core.domain.usecase.SetLastVisitedRouteUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateUserUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateTutorialTaskUseCase
import com.hanbikan.nook.feature.tutorial.navigation.tutorialScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
    getActiveUserUseCase: GetActiveUserUseCase,
    getTutorialDayRangeUseCase: GetTutorialDayRangeUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : ViewModel() {

    // Data for UI
    val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val tutorialDayRange: StateFlow<IntRange?> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(null)
            } else {
                getTutorialDayRangeUseCase(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val tutorialTaskList: StateFlow<List<TutorialTask>> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                getTutorialTasksByUserIdAndDayUseCase(it.id, it.tutorialDay)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    // Ui state
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<TutorialUiState> = tutorialTaskList
        .mapLatest {
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

        viewModelScope.launch(Dispatchers.IO) {
            tutorialTaskList.collectLatest { tutorialTaskList ->
                if (tutorialTaskList.all { it.isDone }) {

                }
            }
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

    fun increaseTutorialDay() {
        viewModelScope.launch(Dispatchers.IO) {
            executeIfBothNonNull(activeUser.value, tutorialDayRange.value) { activeUser, tutorialDayRange ->
                val nextTutorialDay = activeUser.tutorialDay + 1
                if (nextTutorialDay in tutorialDayRange) {
                    updateUserUseCase(activeUser.copy(tutorialDay = nextTutorialDay))
                }
            }
        }
    }

    fun decreaseTutorialDay() {
        viewModelScope.launch(Dispatchers.IO) {
            executeIfBothNonNull(activeUser.value, tutorialDayRange.value) { activeUser, tutorialDayRange ->
                val nextTutorialDay = activeUser.tutorialDay - 1
                if (nextTutorialDay in tutorialDayRange) {
                    updateUserUseCase(activeUser.copy(tutorialDay = nextTutorialDay))
                }
            }
        }
    }
}

sealed interface TutorialUiState {
    object Loading : TutorialUiState
    object Success : TutorialUiState
}