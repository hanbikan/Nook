package com.hanbikan.nook.feature.museum

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.designsystem.component.ChipGroup
import com.hanbikan.nook.core.designsystem.component.ChipItem
import com.hanbikan.nook.core.domain.model.Collectible
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import com.hanbikan.nook.core.domain.usecase.GetAllCollectiblesByUserIdUseCase
import com.hanbikan.nook.feature.museum.navigation.COLLECTIBLE_INDEX_TO_SHOW
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CollectibleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context,
    getActiveUserUseCase: GetActiveUserUseCase,
    getAllCollectiblesByUserIdUseCase: GetAllCollectiblesByUserIdUseCase,
) : ViewModel() {

    private val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val collectibleList: StateFlow<List<Collectible>> = activeUser
        .mapLatest {
            if (it == null) {
                listOf()
            } else {
                getAllCollectiblesByUserIdUseCase(
                    userId = it.id,
                    index = checkNotNull(savedStateHandle[COLLECTIBLE_INDEX_TO_SHOW])
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _monthlyViewType: MutableStateFlow<ChipGroup> = MutableStateFlow(
        ChipGroup(
            chips = listOf(
                ChipItem(context.getString(R.string.overall)),
                ChipItem(context.getString(R.string.monthly))
            ),
            selectedIndex = 0
        )
    )
    val monthlyViewType = _monthlyViewType.asStateFlow()

    fun onClickViewType(index: Int) {
        _monthlyViewType.value =
            monthlyViewType.value.copyWithIndex(index)
    }
}