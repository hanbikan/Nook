package com.hanbikan.nook

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import com.hanbikan.nook.core.domain.usecase.UpdateUserDataUseCase
import com.hanbikan.nook.feature.todo.navigation.tutorialScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appStateRepository: AppStateRepository,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context,
): ViewModel() {

    private val _isReady: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    val users = _users.asStateFlow()

    private val _todoGraphRoute: MutableStateFlow<String> = MutableStateFlow(tutorialScreenRoute)
    val todoGraphRoute = _todoGraphRoute.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            listOf(
                async { loadAllUsers() },
                async { updateUserIfVersionHasChanged() },
                async { loadTodoGraphRoute() }
            ).awaitAll()

            _isReady.value = true
        }
    }

    fun updateUserIfLanguageHasChanged() {
        viewModelScope.launch(Dispatchers.IO) {
            val previousLanguage = appStateRepository.getLanguage().first()
            val currentLanguage = context.resources.configuration.locales.get(0).language

            if (previousLanguage != currentLanguage) {
                // 언어 변경
                appStateRepository.setLanguage(currentLanguage)
                updateUserDataUseCase()
            }
        }
    }

    private suspend fun loadAllUsers() {
        _users.value = userRepository.getAllUsers().first()
    }

    private suspend fun loadTodoGraphRoute() {
        appStateRepository.getTodoGraphRoute().first()?.let {
            _todoGraphRoute.value = it
        }
    }

    private suspend fun updateUserIfVersionHasChanged() {
        val previousVersionName = appStateRepository.getVersionName().first()
        val currentVersionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName

        if (previousVersionName != currentVersionName) {
            // 버전 변경 후 첫 실행
            appStateRepository.setVersionName(currentVersionName)
            updateUserDataUseCase()
        }
    }
}