package com.hanbikan.nook.feature.museum

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.common.getCurrentHour
import com.hanbikan.nook.core.common.getCurrentMonth
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
                        collectibleList = it,
                        month = uiStateValue.month
                    )
                }

                is CollectibleScreenUiState.MonthlyView.HourView -> {
                    CollectibleScreenUiState.MonthlyView.HourView(
                        collectibleList = it,
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
        when (index) {
            CollectibleScreenViewType.OVERALL.chipIndex -> {
                _uiState.value =
                    CollectibleScreenUiState.OverallView(collectibleList = collectibleList.value)
            }

            CollectibleScreenViewType.MONTHLY.chipIndex -> {
                _uiState.value = CollectibleScreenUiState.MonthlyView.GeneralView(
                    collectibleList = collectibleList.value,
                    month = getCurrentMonth()
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
                    month = month
                )
            }

            is CollectibleScreenUiState.MonthlyView.HourView -> {
                _uiState.value = CollectibleScreenUiState.MonthlyView.HourView(
                    collectibleList = collectibleList.value,
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

    fun onClickMonthlyViewType() {
        val uiStateValue = uiState.value
        if (uiStateValue !is CollectibleScreenUiState.MonthlyView) return

        _uiState.value = when (uiStateValue) {
            is CollectibleScreenUiState.MonthlyView.GeneralView -> {
                CollectibleScreenUiState.MonthlyView.HourView(
                    collectibleList.value,
                    uiStateValue.month
                )
            }

            is CollectibleScreenUiState.MonthlyView.HourView -> {
                CollectibleScreenUiState.MonthlyView.GeneralView(
                    collectibleList.value,
                    uiStateValue.month
                )
            }
        }
    }
}

sealed class CollectibleScreenUiState(val chipIndex: Int?) {

    object Loading :
        CollectibleScreenUiState(chipIndex = CollectibleScreenViewType.LOADING.chipIndex)

    class OverallView(val collectibleList: List<Collectible>) :
        CollectibleScreenUiState(chipIndex = CollectibleScreenViewType.OVERALL.chipIndex)

    sealed class MonthlyView(
        val month: Int,
    ) : CollectibleScreenUiState(chipIndex = CollectibleScreenViewType.MONTHLY.chipIndex) {

        class GeneralView(collectibleList: List<Collectible>, month: Int) : MonthlyView(month) {
            val collectibleListForMonth: List<Collectible> =
                getCollectibleListForMonth(collectibleList, month)

            private fun getCollectibleListForMonth(
                collectibleList: List<Collectible>,
                month: Int
            ): List<Collectible> {
                return collectibleList.filter {
                    it is MonthlyCollectible && it.belongsToMonth(month)
                }
            }
        }

        class HourView(collectibleList: List<Collectible>, month: Int) : MonthlyView(month) {
            val startHourToCollectibleListForMonth: Map<Int, List<Collectible>> =
                getStartHourToCollectibleListForMonth(collectibleList, month)

            fun getEndHourByStartHour(startHour: Int): Int {
                val hours = startHourToCollectibleListForMonth.keys.sorted()
                val nextHourIndex = hours.indexOfFirst { it == startHour } + 1
                return hours.getOrElse(nextHourIndex) { 24 }
            }

            fun getKeyForCurrentHour(): Int {
                val currentHour = getCurrentHour()
                val hours = startHourToCollectibleListForMonth.keys.sorted()
                hours.forEach { startHour ->
                    val endHour = getEndHourByStartHour(startHour)
                    if (currentHour in startHour until endHour) {
                        return startHour
                    }
                }
                return ALL_DAY_KEY
            }

            /**
             * Returns {0: <Collectible List 1>, 4: <Collectible List 2>, ..., 21: <Collectible List 3>}
             * which is merged by time range for same lists.
             */
            private fun getStartHourToCollectibleListForMonth(
                collectibleList: List<Collectible>,
                month: Int
            ): Map<Int, List<Collectible>> {
                val startHourToCollectibleListForMonth: MutableMap<Int, List<Collectible>> = mutableMapOf()

                val hourToCollectibleListForMonth = getHourToCollectibleListForMonth(collectibleList, month)
                hourToCollectibleListForMonth.forEach { (hour, collectibleListForHour) ->
                    // Skip if current collectible list is the same as the previous list.
                    if (hour - 1 >= 0 && collectibleListForHour == hourToCollectibleListForMonth[hour - 1]) {
                        return@forEach
                    }

                    startHourToCollectibleListForMonth.put(hour, collectibleListForHour)
                }

                return startHourToCollectibleListForMonth.toMap()
            }

            /**
             * Returns {0: <Collectible List>, 1: <Collectible List>, ..., 23: <Collectible List>}
             */
            private fun getHourToCollectibleListForMonth(
                collectibleList: List<Collectible>,
                month: Int
            ): Map<Int, List<Collectible>> {
                val hourToCollectibleListForMonth = buildMap<Int, MutableList<Collectible>> {
                    put(-1, mutableListOf()) // for always available
                    repeat(24) { hour ->
                        put(hour, mutableListOf())
                    }
                }

                collectibleList.forEach { item ->
                    if (item is MonthlyCollectible && item.belongsToMonth(month)) {
                        val times = item.timesByMonth[month]
                        // 항상 잡을 수 있는 생물은 ALL_DAY_KEY에 추가합니다.
                        if (times == MonthlyCollectible.ALL_DAY) {
                            hourToCollectibleListForMonth[ALL_DAY_KEY]?.add(item)
                        } else {
                            val hours = times?.parseTimeRange() ?: listOf()
                            hours.forEach { hour ->
                                hourToCollectibleListForMonth[hour]?.add(item)
                            }
                        }
                    }
                }

                return hourToCollectibleListForMonth
                    .mapValues { it.value.toList() } // MutableList to List
                    .toMap()
            }

            companion object {
                const val ALL_DAY_KEY = -1
            }
        }
    }
}

private enum class CollectibleScreenViewType(val chipIndex: Int?) {
    LOADING(null), OVERALL(0), MONTHLY(1)
}