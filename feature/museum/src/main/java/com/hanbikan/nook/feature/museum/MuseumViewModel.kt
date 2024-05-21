package com.hanbikan.nook.feature.museum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MuseumViewModel @Inject constructor(
    getActiveUserUseCase: GetActiveUserUseCase,
    collectionRepository: CollectionRepository,
) : ViewModel() {
    // Dialog
    private val _isUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserDialogShown = _isUserDialogShown.asStateFlow()

    val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val fishes: StateFlow<List<Fish>> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                collectionRepository.getAllFishesByUserId(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun switchUserDialog() {
        _isUserDialogShown.value = !isUserDialogShown.value
    }
}