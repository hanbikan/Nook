package com.hanbikan.nook.feature.museum

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.common.getCurrentMonth
import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.model.common.updateOnLocal
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import com.hanbikan.nook.feature.museum.model.CollectibleSequence
import com.hanbikan.nook.feature.museum.navigation.COLLECTIBLE_SEQUENCE_INDEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectibleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getActiveUserUseCase: GetActiveUserUseCase,
    private val collectionRepository: CollectionRepository,
) : ViewModel() {

    // COLLECTIBLE_SEQUENCE_INDEX를 읽어서 bug, fish, sea creature 등을 구분합니다.
    private val collectibleSequence: CollectibleSequence = CollectibleSequence.values()[savedStateHandle[COLLECTIBLE_SEQUENCE_INDEX] ?: 0]

    private val _uiState: MutableStateFlow<CollectibleScreenUiState> = MutableStateFlow(
        CollectibleScreenUiState.Loading
    )
    val uiState: StateFlow<CollectibleScreenUiState> = _uiState

    private val _isHuntingMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isHuntingMode = _isHuntingMode.asStateFlow()

    val collectibleSorts: List<CollectibleSort> = CollectibleSort.getCollectibleSorts(collectibleSequence)

    private val _currentSort: MutableStateFlow<CollectibleSort> = MutableStateFlow(CollectibleSort.SORT_BY_DEFAULT)
    val currentSort = _currentSort.asStateFlow()


    private val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val collectibleList: StateFlow<List<Collectible>> = combine(activeUser, currentSort) { activeUser, currentSort ->
        if (activeUser == null) {
            listOf()
        } else {
            when (collectibleSequence) {
                CollectibleSequence.FISH -> collectionRepository.getAllFishesByUserId(activeUser.id).first()
                CollectibleSequence.BUG -> collectionRepository.getAllBugsByUserId(activeUser.id).first()
                CollectibleSequence.SEA_CREATURE -> collectionRepository.getAllSeaCreaturesByUserId(activeUser.id).first()
            }
        }
    }
        .mapLatest {
            currentSort.value.sort(it)
        }
        .onEach {
            // 기반 데이터가 변경 또는 초기화 되었으므로 uiState를 업데이트 합니다.
            val uiStateValue = uiState.value
            _uiState.value = when (uiStateValue) {
                is CollectibleScreenUiState.MonthlyView.GeneralView -> {
                    CollectibleScreenUiState.MonthlyView.GeneralView(
                        collectibleList = it,
                        month = uiStateValue.month,
                        getIsNorthForActiveUser(),
                    )
                }

                is CollectibleScreenUiState.MonthlyView.HourView -> {
                    CollectibleScreenUiState.MonthlyView.HourView(
                        collectibleList = it,
                        month = uiStateValue.month,
                        getIsNorthForActiveUser(),
                    )
                }

                else -> { // Loading or OverallView
                    CollectibleScreenUiState.OverallView(it)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())


    // Dialogs
    private val _collectibleToShowInDialog: MutableStateFlow<Collectible?> = MutableStateFlow(null)
    val collectibleToShowInDialog = _collectibleToShowInDialog.asStateFlow()

    private val _isInfoDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInfoDialogShown = _isInfoDialogShown


    private val handler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, _ ->
            // TODO: show error message
        }

    fun onClickViewType(index: Int) {
        when (index) {
            CollectibleScreenViewType.OVERALL.chipIndex -> {
                _uiState.value =
                    CollectibleScreenUiState.OverallView(collectibleList = collectibleList.value)
            }

            CollectibleScreenViewType.MONTHLY.chipIndex -> {
                _uiState.value = CollectibleScreenUiState.MonthlyView.HourView(
                    collectibleList = collectibleList.value,
                    month = getCurrentMonth(),
                    getIsNorthForActiveUser(),
                )
            }
        }
    }

    fun onClickMonth(month: Int) {
        val uiStateValue = uiState.value
        if (uiStateValue !is CollectibleScreenUiState.MonthlyView) return

        when (uiStateValue) {
            is CollectibleScreenUiState.MonthlyView.GeneralView -> {
                _uiState.value = CollectibleScreenUiState.MonthlyView.GeneralView(
                    collectibleList = collectibleList.value,
                    month = month,
                    getIsNorthForActiveUser(),
                )
            }

            is CollectibleScreenUiState.MonthlyView.HourView -> {
                _uiState.value = CollectibleScreenUiState.MonthlyView.HourView(
                    collectibleList = collectibleList.value,
                    month = month,
                    getIsNorthForActiveUser(),
                )
            }
        }
    }

    fun onClickCollectibleItem(collectible: Collectible) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            collectible.updateOnLocal(collectionRepository)
        }
    }

    fun onClickMonthlyViewType() {
        val uiStateValue = uiState.value
        if (uiStateValue !is CollectibleScreenUiState.MonthlyView) return

        _uiState.value = when (uiStateValue) {
            is CollectibleScreenUiState.MonthlyView.GeneralView -> {
                CollectibleScreenUiState.MonthlyView.HourView(
                    collectibleList.value,
                    uiStateValue.month,
                    getIsNorthForActiveUser(),
                )
            }

            is CollectibleScreenUiState.MonthlyView.HourView -> {
                CollectibleScreenUiState.MonthlyView.GeneralView(
                    collectibleList.value,
                    uiStateValue.month,
                    getIsNorthForActiveUser(),
                )
            }
        }
    }

    fun onLongClickCollectibleItem(collectible: Collectible) {
        _collectibleToShowInDialog.value = collectible
    }

    fun onDismissCollectibleDialog() {
        _collectibleToShowInDialog.value = null
    }

    fun switchIsInfoDialogShown() {
        _isInfoDialogShown.value = !isInfoDialogShown.value
    }
    
    fun getIsNorthForActiveUser(): Boolean {
        return activeUser.value?.isNorth ?: true
    }

    fun switchIsHuntingMode() {
        _isHuntingMode.value = !isHuntingMode.value
    }

    fun setCurrentSort(collectibleSort: CollectibleSort) {
        _currentSort.value = collectibleSort
    }
}

enum class CollectibleScreenViewType(val chipIndex: Int?) {
    LOADING(null), OVERALL(0), MONTHLY(1)
}