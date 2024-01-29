package com.hanbikan.nook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.usecase.GetLastVisitedRouteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLastVisitedRouteUseCase: GetLastVisitedRouteUseCase
): ViewModel() {

    private val _isReady: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _lastVisitedRoute: MutableStateFlow<String?> = MutableStateFlow(null)
    val lastVisitedRoute = _lastVisitedRoute.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLastVisitedRouteUseCase().collect {
                _isReady.value = true
                _lastVisitedRoute.value = it
            }
        }
    }
}