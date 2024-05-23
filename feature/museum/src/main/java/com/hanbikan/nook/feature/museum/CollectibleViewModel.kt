package com.hanbikan.nook.feature.museum

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.Collectible
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.MonthlyCollectible
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.model.parseTimeRange
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CollectibleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getActiveUserUseCase: GetActiveUserUseCase,
    private val collectionRepository: CollectionRepository,
) : ViewModel() {

    private val collectibleSequenceIndex: Int? = savedStateHandle[COLLECTIBLE_SEQUENCE_INDEX]

    private val _uiState: MutableStateFlow<CollectibleScreenUiState> = MutableStateFlow(
        CollectibleScreenUiState.Loading
    )
    val uiState: StateFlow<CollectibleScreenUiState> = _uiState

    private val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val handler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            // TODO: show error message
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val collectibleList: StateFlow<List<Collectible>> = activeUser
        .flatMapLatest {
            if (it == null || collectibleSequenceIndex == null) {
                flowOf(listOf())
            } else {
                when (collectibleSequenceIndex) {
                    CollectibleSequence.FISH.ordinal -> collectionRepository.getAllFishesByUserId(
                        userId = it.id
                    )

                    else -> flowOf(listOf())
                }
            }
        }
        .onEach {
            // 기반 데이터가 변경 또는 초기화 되었으므로 uiState를 업데이트 합니다.
            val uiStateValue = uiState.value
            _uiState.value = when (uiStateValue) {
                is CollectibleScreenUiState.MonthlyView.GeneralView -> {
                    CollectibleScreenUiState.MonthlyView.GeneralView(
                        collectibleList = getCollectibleListForMonth(it, uiStateValue.month),
                        month = uiStateValue.month
                    )
                }

                is CollectibleScreenUiState.MonthlyView.HourView -> {
                    CollectibleScreenUiState.MonthlyView.HourView(
                        collectibleList = getCollectibleListForMonth(it, uiStateValue.month),
                        month = uiStateValue.month
                    )
                }

                else -> { // Loading or OverallView
                    CollectibleScreenUiState.OverallView(it)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    fun onClickViewType(index: Int) {
        _uiState.value = when (index) {
            0 -> {
                CollectibleScreenUiState.OverallView(collectibleList = collectibleList.value)
            }

            else -> {
                val month = getCurrentMonth()
                CollectibleScreenUiState.MonthlyView.GeneralView(
                    collectibleList = getCollectibleListForMonth(collectibleList.value, month),
                    month = month
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
                    collectibleList = getCollectibleListForMonth(collectibleList.value, month),
                    month = month
                )
            }

            is CollectibleScreenUiState.MonthlyView.HourView -> {
                _uiState.value = CollectibleScreenUiState.MonthlyView.HourView(
                    collectibleList = getCollectibleListForMonth(collectibleList.value, month),
                    month = month
                )
            }
        }
    }

    fun onClickCollectibleItem(collectible: Collectible) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val item: Collectible =
                collectibleList.value.find { it == collectible } ?: return@launch
            when (collectibleSequenceIndex) {
                CollectibleSequence.FISH.ordinal -> {
                    val fish = (item as Fish).copy(isCollected = !item.isCollected)
                    collectionRepository.updateFish(fish)
                }
            }
        }
    }

    private fun getCollectibleListForMonth(
        collectibleList: List<Collectible>,
        month: Int
    ): List<Collectible> {
        return collectibleList.filter {
            it is MonthlyCollectible && it.belongsToMonth(month)
        }
    }

    private fun getCurrentMonth(): Int {
        val calendar: Calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }
}

sealed class CollectibleScreenUiState(val chipIndex: Int?) {

    object Loading : CollectibleScreenUiState(chipIndex = null)

    class OverallView(val collectibleList: List<Collectible>) :
        CollectibleScreenUiState(chipIndex = 0)

    sealed class MonthlyView(
        val collectibleList: List<Collectible>,
        val month: Int,
    ) : CollectibleScreenUiState(chipIndex = 1) {
        class GeneralView(
            collectibleList: List<Collectible>,
            month: Int
        ) : MonthlyView(collectibleList, month)

        class HourView(
            collectibleList: List<Collectible>,
            month: Int
        ) : MonthlyView(collectibleList, month) {

            val hourToCollectibleList = getHourToCollectibleListForMonth(collectibleList, month)

            private fun getHourToCollectibleListForMonth(
                collectibleList: List<Collectible>,
                month: Int
            ): Map<Int, List<Collectible>> {
                val map = buildMap<Int, MutableList<Collectible>> {
                    repeat(24) { hour ->
                        put(hour, mutableListOf())
                    }
                }

                collectibleList.forEach { item ->
                    if (item is MonthlyCollectible && item.belongsToMonth(month)) {
                        val times = item.timesByMonth[month]
                        val hours = times?.parseTimeRange() ?: listOf()
                        hours.forEach { hour ->
                            map[hour]?.add(item)
                        }
                    }
                }

                return map
                    .mapValues { it.value.toList() }
                    .toMap()
            }
        }
    }
}