package com.hanbikan.nook.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDialogViewModel @Inject constructor(
    getActiveUserUseCase: GetActiveUserUseCase,
    private val userRepository: UserRepository,
    private val appStateRepository: AppStateRepository
) : ViewModel() {

    val users: StateFlow<List<User>> = userRepository.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _userIdToDelete: MutableStateFlow<Int?> = MutableStateFlow(null)
    val userIdToDelete = _userIdToDelete.asStateFlow()

    private val _isDeleteUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDeleteUserDialogShown = _isDeleteUserDialogShown.asStateFlow()

    fun setActiveUserId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            appStateRepository.setActiveUserId(id)
        }
    }

    fun setUserIdToDelete(id: Int) {
        _userIdToDelete.value = id
    }

    fun onConfirmDeleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userIdToDelete.value?.let {
                userRepository.deleteUserById(it)
                switchIsDeleteUserDialogShown()
            }
        }
    }

    fun switchIsDeleteUserDialogShown() {
        _isDeleteUserDialogShown.value = !isDeleteUserDialogShown.value
    }
}