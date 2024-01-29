package com.hanbikan.nook.feature.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.usecase.SetLastVisitedRouteUseCase
import com.hanbikan.nook.feature.tutorial.navigation.tutorialScreenRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    setLastVisitedRouteUseCase: SetLastVisitedRouteUseCase,
) : ViewModel() {
    // Dialog
    private val _isUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserDialogShown = _isUserDialogShown.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            setLastVisitedRouteUseCase(tutorialScreenRoute)
        }
    }

    fun switchUserDialog() {
        _isUserDialogShown.value = !isUserDialogShown.value
    }
}