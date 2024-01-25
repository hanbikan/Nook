package com.hanbikan.nook.ui

import androidx.compose.runtime.Composable
import com.hanbikan.nook.core.domain.usecase.GetAllUsersUseCase
import com.hanbikan.nook.navigation.NkAppState
import com.hanbikan.nook.navigation.NkNavHost
import com.hanbikan.nook.navigation.rememberNkAppState

@Composable
fun NkApp(
    startDestination: String,
    appState: NkAppState = rememberNkAppState(
        startDestination = startDestination,
    ),
) {
    NkNavHost(appState = appState)
}