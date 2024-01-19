package com.hanbikan.nooknook.feature.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nooknook.core.domain.model.User
import com.hanbikan.nooknook.core.domain.usecase.AddUserUseCase
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

    fun setName(newName: String) {
        if (newName.length >= 10) return
        _name.value = newName
    }

    fun setIslandName(newIslandName: String) {
        if (newIslandName.length >= 10) return
        _islandName.value = newIslandName
    }

    fun addUser(onComplete: () -> Unit) {
        if (name.value.isEmpty() || islandName.value.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            val user = User(0, name.value, islandName.value)
            addUserUseCase(user)

            // onComplete에서 navigate와 같은 동작을 수행하므로 Main Thread에서 수행되어야 합니다.
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }
}