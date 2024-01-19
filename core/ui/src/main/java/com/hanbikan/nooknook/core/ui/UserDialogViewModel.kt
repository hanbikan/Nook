package com.hanbikan.nooknook.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.usecase.DeleteUserByIdUseCase
import com.hanbikan.nooknook.core.domain.usecase.GetActiveUserIdUseCase
import com.hanbikan.nooknook.core.domain.usecase.GetAllUsersUseCase
import com.hanbikan.nooknook.core.domain.usecase.SetActiveUserIdUseCase
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
    private val deleteUserByIdUseCase: DeleteUserByIdUseCase,
    getActiveUserIdUseCase: GetActiveUserIdUseCase,
    private val setActiveUserIdUseCase: SetActiveUserIdUseCase,
) : ViewModel() {

    val users: StateFlow<List<User>> = getAllUsersUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    val activeUserId: StateFlow<Int?> = getActiveUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _userIdToDelete: MutableStateFlow<Int?> = MutableStateFlow(null)
    val userIdToDelete = _userIdToDelete.asStateFlow()

    private val _isDeleteUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDeleteUserDialogShown = _isDeleteUserDialogShown.asStateFlow()

    fun onClickUser(user: User) {
        if (activeUserId.value == user.id) return

        viewModelScope.launch(Dispatchers.IO) {
            setActiveUserIdUseCase(user.id)
        }
    }

    fun onLongClickUser(user: User) {
        if (activeUserId.value == user.id) return

        _userIdToDelete.value = user.id
        switchIsDeleteUserDialogShown()
    }

    fun onConfirmDeleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userIdToDelete.value?.let {
                deleteUserByIdUseCase(it)
                switchIsDeleteUserDialogShown()
            }
        }
    }

    fun switchIsDeleteUserDialogShown() {
        _isDeleteUserDialogShown.value = !isDeleteUserDialogShown.value
    }
}