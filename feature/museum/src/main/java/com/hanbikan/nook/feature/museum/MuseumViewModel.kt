package com.hanbikan.nook.feature.museum

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.designsystem.component.ChipGroup
import com.hanbikan.nook.core.designsystem.component.ChipItem
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import com.hanbikan.nook.core.domain.usecase.GetFishesByUserIdUseCase
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
class MuseumViewModel @Inject constructor(
    private val getFishesByUserIdUseCase: GetFishesByUserIdUseCase,
    getActiveUserUseCase: GetActiveUserUseCase,
    @ApplicationContext context: Context,
) : ViewModel() {

    private val _viewTypeChipGroup: MutableStateFlow<ChipGroup> = MutableStateFlow(
        ChipGroup(
            chips = listOf(
                ChipItem(context.getString(R.string.overall)),
                ChipItem(context.getString(R.string.monthly))
            ),
            selectedIndex = 0
        )
    )
    val viewTypeChipGroup = _viewTypeChipGroup.asStateFlow()

    // Dialog
    private val _isUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserDialogShown = _isUserDialogShown.asStateFlow()

    val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val fishList: StateFlow<List<Fish>> = activeUser
        .mapLatest {
            if (it == null) {
                listOf()
            } else {
                getFishesByUserIdUseCase(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())


    fun switchUserDialog() {
        _isUserDialogShown.value = !isUserDialogShown.value
    }

    fun onClickViewType(index: Int) {
        _viewTypeChipGroup.value = viewTypeChipGroup.value.copyWithIndex(index)
    }
}