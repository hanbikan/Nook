package com.hanbikan.nooknook.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserDialogViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {
    val users: StateFlow<List<User>> = getAllUsersUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())
}