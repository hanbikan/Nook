package com.hanbikan.nooknook.feature.phone.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.hanbikan.nooknook.feature.todo.navigation.navigateToTodo
import com.hanbikan.nooknook.feature.todo.navigation.todoScreen
import com.hanbikan.nooknook.feature.tutorial.navigation.addUserScreen
import com.hanbikan.nooknook.feature.tutorial.navigation.navigateToAddUser
import com.hanbikan.nooknook.feature.tutorial.navigation.navigateToWelcome
import com.hanbikan.nooknook.feature.tutorial.navigation.welcomeScreen
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
            navigateToWelcome = navController::navigateToWelcome,
            navigateToTodo = navController::navigateToTodo,
        )
        // TODO: PhoneScreen
        welcomeScreen(
            navigateToAddUser = navController::navigateToAddUser,
        )
        addUserScreen(
            navigateUp = navController::navigateUp,
            navigateToTutorial = { /* TODO */ }
        )

        todoScreen()
    }
}