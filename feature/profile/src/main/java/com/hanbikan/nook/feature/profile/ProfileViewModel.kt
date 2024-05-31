package com.hanbikan.nook.feature.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.UserRepository
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
    private val userRepository: UserRepository,
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

    private val _toastMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val toastMessage = _toastMessage.asStateFlow()

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
                userRepository.insertUser(newUser)
            }
            switchUpdateNameDialog()
        }
    }

    fun onConfirmUpdateIslandName(islandName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            activeUser.value?.let {
                val newUser = it.copy(islandName = islandName)
                userRepository.insertUser(newUser)
            }
            switchUpdateIslandNameDialog()
        }
    }

    fun onClickUpdateUser(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            activeUser.value?.let {
                updateUserUseCase.invoke(it.id)
                setToastMessage(context.getString(R.string.user_data_updated))
            }
        }
    }

    fun setIsNorth(newIsNorth: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            activeUser.value?.let {
                val newUser = it.copy(isNorth = newIsNorth)
                userRepository.insertUser(newUser)
            }
        }
    }

    fun setToastMessage(message: String?) {
        _toastMessage.value = message
    }
}