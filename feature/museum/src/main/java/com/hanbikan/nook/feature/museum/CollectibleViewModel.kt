package com.hanbikan.nook.feature.museum

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.common.getCurrentMonth
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.model.common.Collectible
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
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
    private val collectibleSequence: CollectibleSequence =
        CollectibleSequence.values()[savedStateHandle[COLLECTIBLE_SEQUENCE_INDEX] ?: 0]

    private val _isHuntingMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isHuntingMode = _isHuntingMode.asStateFlow()


    private val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val collectibleSorts: List<CollectibleSort> = CollectibleSort.getCollectibleSorts(collectibleSequence)
    private val _sort: MutableStateFlow<CollectibleSort> = MutableStateFlow(CollectibleSort.SORT_BY_DEFAULT)
    val sort = _sort.asStateFlow()

    val collectibleFilters: List<CollectibleFilter> = CollectibleFilter.getCollectibleFilters()
    private val _filter: MutableStateFlow<CollectibleFilter> = MutableStateFlow(CollectibleFilter.FILTER_BY_DEFAULT)
    val filter = _filter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val collectibleList: StateFlow<List<Collectible>> =
        combine(activeUser, sort, _filter) { activeUser, _, _ ->
            if (activeUser == null) {
                flowOf(listOf())
            } else {
                when (collectibleSequence) {
                    CollectibleSequence.FISH -> collectionRepository.getAllFishesByUserId(activeUser.id)
                    CollectibleSequence.BUG -> collectionRepository.getAllBugsByUserId(activeUser.id)
                    CollectibleSequence.SEA_CREATURE -> collectionRepository.getAllSeaCreaturesByUserId(activeUser.id)
                }
            }
        }
            .flatMapLatest { it }
            .mapLatest { _filter.value.filter(it) }
            .mapLatest { sort.value.sort(it) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    val isNorth: StateFlow<Boolean> = activeUser.mapLatest { it?.isNorth ?: true }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    private val month: MutableStateFlow<Int> = MutableStateFlow(getCurrentMonth())

    private val viewType: MutableStateFlow<CollectibleScreenViewType> =
        MutableStateFlow(CollectibleScreenViewType.OVERALL)

    val uiState: StateFlow<CollectibleScreenUiState> = combine(
        collectibleList,
        viewType,
        month,
        isNorth
    ) { collectibleList, viewType, month, isNorth ->
        when (viewType) {
            CollectibleScreenViewType.LOADING -> {
                CollectibleScreenUiState.Loading
            }
            CollectibleScreenViewType.OVERALL -> {
                _isHuntingMode.value = false
                _sort.value = CollectibleSort.SORT_BY_DEFAULT
                CollectibleScreenUiState.OverallView(collectibleList)
            }
            CollectibleScreenViewType.MONTHLY_GENERAL -> {
                _isHuntingMode.value = true
                _sort.value = CollectibleSort.SORT_BY_IS_COLLECTED
                CollectibleScreenUiState.MonthlyView.GeneralView(collectibleList, month, isNorth)
            }
            CollectibleScreenViewType.MONTHLY_HOUR -> {
                _isHuntingMode.value = true
                _sort.value = CollectibleSort.SORT_BY_IS_COLLECTED
                CollectibleScreenUiState.MonthlyView.HourView(collectibleList, month, isNorth)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), CollectibleScreenUiState.Loading)


    // Dialogs
    private val _collectibleForDetailCollectibleDialog: MutableStateFlow<Collectible?> = MutableStateFlow(null)
    val collectibleForDetailCollectibleDialog = _collectibleForDetailCollectibleDialog.asStateFlow()

    private val _collectibleForCollectDialog: MutableStateFlow<Collectible?> = MutableStateFlow(null)
    val collectibleForCollectDialog = _collectibleForCollectDialog.asStateFlow()

    private val _isInfoDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInfoDialogShown = _isInfoDialogShown


    private val handler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, _ ->
            // TODO: show error message
        }

    fun onClickViewTypeChip(chipIndex: Int) {
        viewType.value = if (chipIndex == 0) {
            CollectibleScreenViewType.OVERALL
        } else {
            CollectibleScreenViewType.MONTHLY_HOUR
        }
    }

    fun onClickMonthlyViewType() {
        viewType.value = if (viewType.value == CollectibleScreenViewType.MONTHLY_GENERAL) {
            CollectibleScreenViewType.MONTHLY_HOUR
        } else {
            CollectibleScreenViewType.MONTHLY_GENERAL
        }
    }

    fun onClickMonth(month: Int) {
        this.month.value = month
    }

    fun onClickCollectibleItem(collectible: Collectible) {
        _collectibleForCollectDialog.value = collectible
    }

    fun onLongClickCollectibleItem(collectible: Collectible) {
        _collectibleForDetailCollectibleDialog.value = collectible
    }

    fun onDismissDetailCollectibleDialog() {
        _collectibleForDetailCollectibleDialog.value = null
    }

    fun onConfirmCollectDialog() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            collectibleForCollectDialog.value?.updateOnLocal(collectionRepository)
            _collectibleForCollectDialog.value = null
        }
    }

    fun onDismissCollectDialog() {
        _collectibleForCollectDialog.value = null
    }

    fun switchIsInfoDialogShown() {
        _isInfoDialogShown.value = !isInfoDialogShown.value
    }

    fun switchIsHuntingMode() {
        _isHuntingMode.value = !isHuntingMode.value
    }

    fun setSort(collectibleSort: CollectibleSort) {
        _sort.value = collectibleSort
    }

    fun setFilter(collectibleFilter: CollectibleFilter) {
        _filter.value = collectibleFilter
    }
}