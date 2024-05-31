package com.hanbikan.nook.feature.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.usecase.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
) : ViewModel() {

    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _islandName: MutableStateFlow<String> = MutableStateFlow("")
    val islandName = _islandName.asStateFlow()

    private val _isNorth: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isNorth = _isNorth.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setName(newName: String) {
        if (newName.length >= User.NAME_MAX_LENGTH) return
        _name.value = newName
    }

    fun setIslandName(newIslandName: String) {
        if (newIslandName.length >= User.ISLAND_NAME_MAX_LENGTH) return
        _islandName.value = newIslandName
    }

    fun setIsNorth(newIsNorth: Boolean) {
        _isNorth.value = newIsNorth
    }

    fun addUser(onComplete: () -> Unit) {
        if (name.value.isEmpty() || islandName.value.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            val user = User(name = name.value, islandName = islandName.value)
            addUserUseCase(user)

            // onComplete에서 navigate와 같은 동작을 수행하므로 Main Thread에서 수행되어야 합니다.
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }

    fun setIsLoading(newIsLoading: Boolean) {
        _isLoading.value = newIsLoading
    }
}