package com.hanbikan.nookie.ui

import androidx.compose.runtime.Composable
import com.hanbikan.nookie.core.domain.usecase.GetAllUsersUseCase
import com.hanbikan.nookie.navigation.NkAppState
import com.hanbikan.nookie.navigation.NkNavHost
import com.hanbikan.nookie.navigation.rememberNkAppState

@Composable
fun NkApp(
    getAllUsersUseCase: GetAllUsersUseCase,
    appState: NkAppState = rememberNkAppState(
        getAllUsersUseCase = getAllUsersUseCase,
    ),
) {
    NkNavHost(appState = appState)
}