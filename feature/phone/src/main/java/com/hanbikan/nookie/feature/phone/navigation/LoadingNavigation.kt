package com.hanbikan.nookie.feature.phone.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nookie.feature.phone.LoadingScreen
import kotlinx.coroutines.flow.Flow

const val loadingScreenRoute = "loading_screen_route"

fun NavGraphBuilder.loadingScreen(
    hasAnyUsers: Flow<Boolean>,
    navigateToWelcome: () -> Unit,
    navigateToTodo: () -> Unit,
) {
    composable(
        route = loadingScreenRoute,
    ) {
        LoadingScreen(
            hasAnyUsers = hasAnyUsers,
            navigateToWelcome = navigateToWelcome,
            navigateToTodo = navigateToTodo,
        )
    }
}