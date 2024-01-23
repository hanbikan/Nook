package com.hanbikan.nook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hanbikan.nook.core.domain.usecase.GetAllUsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest

@Composable
fun rememberNkAppState(
    getAllUsersUseCase: GetAllUsersUseCase,
    navController: NavHostController = rememberNavController(),
): NkAppState {
    return remember(
        getAllUsersUseCase,
        navController,
    ) {
        NkAppState(
            navController,
            getAllUsersUseCase,
        )
    }
}

class NkAppState(
    val navController: NavHostController,
    getAllUsersUseCase: GetAllUsersUseCase,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    val hasAnyUsers = getAllUsersUseCase().mapLatest { users ->
        users.isNotEmpty()
    }
}