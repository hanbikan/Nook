package com.hanbikan.nook.feature.museum

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.Collectible
import com.hanbikan.nook.core.domain.model.MonthlyCollectible
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import com.hanbikan.nook.core.domain.usecase.GetFishesByUserIdUseCase
import com.hanbikan.nook.feature.museum.model.CollectibleSequence
import com.hanbikan.nook.feature.museum.navigation.COLLECTIBLE_SEQUENCE_INDEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CollectibleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getActiveUserUseCase: GetActiveUserUseCase,
    getFishesByUserIdUseCase: GetFishesByUserIdUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<CollectibleScreenUiState> = MutableStateFlow(
        CollectibleScreenUiState.Loading
    )
    val uiState: StateFlow<CollectibleScreenUiState> = _uiState

    private val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val collectibleList: StateFlow<List<Collectible>> = activeUser
        .mapLatest {
            val collectibleSequenceIndex: Int? = savedStateHandle[COLLECTIBLE_SEQUENCE_INDEX]
            if (it == null || collectibleSequenceIndex == null) {
                listOf()
            } else {
                when (collectibleSequenceIndex) {
                    CollectibleSequence.FISH.ordinal -> getFishesByUserIdUseCase(userId = it.id)
                    else -> listOf()
                }
            }
        }
        .onEach {
            if (it.isNotEmpty()) {
                _uiState.value = CollectibleScreenUiState.OverallView(it)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun onClickViewType(index: Int) {
        _uiState.value = when (index) {
            0 -> {
                CollectibleScreenUiState.OverallView(collectibleList = collectibleList.value)
            }
            else -> {
                CollectibleScreenUiState.MonthlyView.GeneralView(collectibleList = collectibleList.value)
            }
        }
    }

    fun onClickCollectibleItem(index: Int) {
        // TODO
    }
}

sealed class CollectibleScreenUiState(val chipIndex: Int?) {

    object Loading : CollectibleScreenUiState(chipIndex = null)

    class OverallView(collectibleList: List<Collectible>) : CollectibleScreenUiState(chipIndex = 0)

    sealed class MonthlyView : CollectibleScreenUiState(chipIndex = 1) {
        class GeneralView(collectibleList: List<Collectible>) : MonthlyView()
        class HourView(hourToCollectibleList: Map<Int, List<MonthlyCollectible>>) : MonthlyView()
    }
}