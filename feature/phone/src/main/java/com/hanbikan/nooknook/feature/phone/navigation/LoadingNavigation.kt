package com.hanbikan.nooknook.feature.phone.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nooknook.feature.phone.LoadingScreen
import kotlinx.coroutines.flow.Flow

const val loadingScreenRoute = "loading_screen_route"

fun NavGraphBuilder.loadingScreen(
    hasAnyUsers: Flow<Boolean>,
    navigateToTutorial: () -> Unit,
    navigateToTodo: () -> Unit,
) {
    composable(
        route = loadingScreenRoute,
    ) {
        LoadingScreen(
            hasAnyUsers = hasAnyUsers,
            navigateToTutorial = navigateToTutorial,
            navigateToTodo = navigateToTodo,
        )
    }
}