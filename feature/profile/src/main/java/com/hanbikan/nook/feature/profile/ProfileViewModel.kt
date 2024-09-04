package com.hanbikan.nook.feature.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.common.getCalendar
import com.hanbikan.nook.core.common.getMinutesDifference
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateUserDataUseCase
import com.hanbikan.nook.core.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    getActiveUserUseCase: GetActiveUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
) : ViewModel() {
    val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _islandTime: MutableStateFlow<Calendar?> = MutableStateFlow(null)
    val islandTime = _islandTime.asStateFlow()

    // Dialog
    private val _isUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserDialogShown = _isUserDialogShown.asStateFlow()

    private val _isUpdateNameDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUpdateNameDialogShown = _isUpdateNameDialogShown.asStateFlow()

    private val _isUpdateIslandNameDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUpdateIslandNameDialogShown = _isUpdateIslandNameDialogShown.asStateFlow()

    private val _isDateTimePickerDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDateTimePickerDialogShown = _isDateTimePickerDialogShown.asStateFlow()

    private val _toastMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val toastMessage = _toastMessage.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            activeUser.filterNotNull().collectLatest {
                while (true) {
                    _islandTime.value = getCalendar(it.minuteOffset)
                    delay(10_000)
                }
            }
        }
    }

    fun switchUserDialog() {
        _isUserDialogShown.value = !isUserDialogShown.value
    }

    fun switchUpdateNameDialog() {
        _isUpdateNameDialogShown.value = !_isUpdateNameDialogShown.value
    }

    fun switchUpdateIslandNameDialog() {
        _isUpdateIslandNameDialogShown.value = !isUpdateIslandNameDialogShown.value
    }

    fun switchDateTimePickerDialog() {
        _isDateTimePickerDialogShown.value = !isDateTimePickerDialogShown.value
    }

    fun onConfirmUpdateName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isEmpty()) {
                setToastMessage(context.getString(com.hanbikan.nook.core.designsystem.R.string.empty_name))
            } else {
                activeUser.value?.let {
                    val newUser = it.copy(name = name)
                    updateUserUseCase(newUser)
                }
                switchUpdateNameDialog()
            }
        }
    }

    fun onConfirmUpdateIslandName(islandName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (islandName.isEmpty()) {
                setToastMessage(context.getString(com.hanbikan.nook.core.designsystem.R.string.empty_island_name))
            } else {
                activeUser.value?.let {
                    val newUser = it.copy(islandName = islandName)
                    updateUserUseCase(newUser)
                }
                switchUpdateIslandNameDialog()
            }
        }
    }

    fun setIsNorth(newIsNorth: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            activeUser.value?.let {
                val newUser = it.copy(isNorth = newIsNorth)
                updateUserUseCase(newUser)
            }
        }
    }

    fun onClickUpdateUser() {
        viewModelScope.launch(Dispatchers.IO) {
            activeUser.value?.let {
                updateUserDataUseCase.invoke(it.id)
                setToastMessage(context.getString(R.string.user_data_updated))
            }
        }
    }

    fun setToastMessage(message: String?) {
        _toastMessage.value = message
    }

    fun onDateTimeSelected(calendar: Calendar) {
        activeUser.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val currentCalendar = Calendar.getInstance()
                val minuteOffset = calendar.getMinutesDifference(currentCalendar)
                updateUserUseCase.invoke(it.copy(minuteOffset = minuteOffset))
            }
        }
        switchDateTimePickerDialog()
    }
}