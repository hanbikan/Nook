package com.hanbikan.nook.feature.phone

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor(

): ViewModel() {
    // Dialog
    private val _isUserDialogShown: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserDialogShown = _isUserDialogShown.asStateFlow()

    fun switchUserDialog() {
        _isUserDialogShown.value = !isUserDialogShown.value
    }
}