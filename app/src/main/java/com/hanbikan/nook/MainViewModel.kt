package com.hanbikan.nook

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.usecase.UpdateUserDataUseCase
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
    @ApplicationContext private val context: Context,
): ViewModel() {

    private val _isReady: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _lastVisitedRoute: MutableStateFlow<String?> = MutableStateFlow(null)
    val lastVisitedRoute = _lastVisitedRoute.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            listOf(
                async { loadLastVisitedRoute() },
                async { updateUserIfVersionHasChanged() },
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

    private suspend fun loadLastVisitedRoute() {
        _lastVisitedRoute.value = appStateRepository.getLastVisitedRoute().first()
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