package com.hanbikan.nook.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateUserUseCase
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
class ProfileViewModel @Inject constructor(
    getActiveUserUseCase: GetActiveUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : ViewModel() {
    val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    // Dialog
    private val _isUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserDialogShown = _isUserDialogShown.asStateFlow()

    private val _isUpdateNameDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUpdateNameDialogShown = _isUpdateNameDialogShown.asStateFlow()

    private val _isUpdateIslandNameDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUpdateIslandNameDialogShown = _isUpdateIslandNameDialogShown.asStateFlow()

    fun switchUserDialog() {
        _isUserDialogShown.value = !isUserDialogShown.value
    }

    fun switchUpdateNameDialog() {
        _isUpdateNameDialogShown.value = !_isUpdateNameDialogShown.value
    }

    fun switchUpdateIslandNameDialog() {
        _isUpdateIslandNameDialogShown.value = !isUpdateIslandNameDialogShown.value
    }

    fun onConfirmUpdateName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            activeUser.value?.let {
                val newUser = it.copy(name = name)
                updateUserUseCase(newUser)
            }
            switchUpdateNameDialog()
        }
    }

    fun onConfirmUpdateIslandName(islandName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            activeUser.value?.let {
                val newUser = it.copy(islandName = islandName)
                updateUserUseCase(newUser)
            }
            switchUpdateIslandNameDialog()
        }
    }
}