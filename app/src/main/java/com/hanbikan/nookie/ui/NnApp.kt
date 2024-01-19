package com.hanbikan.nookie.ui

import androidx.compose.runtime.Composable
import com.hanbikan.nookie.core.domain.usecase.GetAllUsersUseCase
import com.hanbikan.nookie.navigation.NnAppState
import com.hanbikan.nookie.navigation.NnNavHost
import com.hanbikan.nookie.navigation.rememberNnAppState

@Composable
fun NnApp(
    getAllUsersUseCase: GetAllUsersUseCase,
    appState: NnAppState = rememberNnAppState(
        getAllUsersUseCase = getAllUsersUseCase,
    ),
) {
    NnNavHost(appState = appState)
}