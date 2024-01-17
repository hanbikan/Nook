package com.hanbikan.nooknook.ui

import androidx.compose.runtime.Composable
import com.hanbikan.nooknook.core.domain.usecase.GetAllUsersUseCase
import com.hanbikan.nooknook.navigation.NnAppState
import com.hanbikan.nooknook.navigation.NnNavHost
import com.hanbikan.nooknook.navigation.rememberNnAppState

@Composable
fun NnApp(
    getAllUsersUseCase: GetAllUsersUseCase,
    appState: NnAppState = rememberNnAppState(
        getAllUsersUseCase = getAllUsersUseCase,
    ),
) {
    NnNavHost(appState = appState)
}