package com.hanbikan.nooknook.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.usecase.DeleteUserUseCase
import com.hanbikan.nooknook.core.domain.usecase.GetAllUsersUseCase
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
    getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) : ViewModel() {

    val users: StateFlow<List<User>> = getAllUsersUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _userIdToDelete: MutableStateFlow<Int> = MutableStateFlow(-1)
    val userIdToDelete = _userIdToDelete.asStateFlow()

    private val _isDeleteUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDeleteUserDialogShown = _isDeleteUserDialogShown.asStateFlow()

    fun onLongClickUser(user: User) {
        _userIdToDelete.value = user.id
        switchIsDeleteUserDialogShown()
    }

    fun onConfirmDeleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserUseCase(userIdToDelete.value)
            switchIsDeleteUserDialogShown()
        }
    }

    fun switchIsDeleteUserDialogShown() {
        _isDeleteUserDialogShown.value = !isDeleteUserDialogShown.value
    }
}