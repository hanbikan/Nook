package com.hanbikan.nooknook.feature.phone.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.hanbikan.nooknook.feature.todo.navigation.navigateToTodo
import com.hanbikan.nooknook.feature.todo.navigation.todoScreen
import com.hanbikan.nooknook.feature.tutorial.navigation.navigateToTutorial
import com.hanbikan.nooknook.feature.tutorial.navigation.tutorialScreen
import kotlinx.coroutines.flow.Flow

const val phoneGraphRoute = "phone_graph"


fun NavGraphBuilder.phoneGraph(
    hasAnyUsers: Flow<Boolean>,
    navController: NavHostController,
    // TODO: lastVisitedApp
) {
    navigation(
        route = phoneGraphRoute,
        startDestination = loadingScreenRoute,
    ) {
        loadingScreen(
            hasAnyUsers = hasAnyUsers,
            navigateToTutorial = navController::navigateToTutorial,
            navigateToTodo = navController::navigateToTodo,
        )
        // TODO: PhoneScreen
        tutorialScreen()
        todoScreen()
    }
}