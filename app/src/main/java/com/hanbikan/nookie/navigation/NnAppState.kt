package com.hanbikan.nookie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hanbikan.nookie.core.domain.usecase.GetAllUsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest

@Composable
fun rememberNnAppState(
    getAllUsersUseCase: GetAllUsersUseCase,
    navController: NavHostController = rememberNavController(),
): NnAppState {
    return remember(
        getAllUsersUseCase,
        navController,
    ) {
        NnAppState(
            navController,
            getAllUsersUseCase,
        )
    }
}

class NnAppState(
    val navController: NavHostController,
    getAllUsersUseCase: GetAllUsersUseCase,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    val hasAnyUsers = getAllUsersUseCase().mapLatest { users ->
        users.isNotEmpty()
    }
}