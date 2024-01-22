package com.hanbikan.nookie.feature.phone.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hanbikan.nookie.feature.phone.PhoneScreen
import com.hanbikan.nookie.feature.todo.navigation.navigateToTodo
import com.hanbikan.nookie.feature.todo.navigation.todoScreen
import com.hanbikan.nookie.feature.tutorial.navigation.addUserScreen
import com.hanbikan.nookie.feature.tutorial.navigation.navigateToAddUser
import com.hanbikan.nookie.feature.tutorial.navigation.navigateToTutorial
import com.hanbikan.nookie.feature.tutorial.navigation.navigateToWelcome
import com.hanbikan.nookie.feature.tutorial.navigation.tutorialScreen
import com.hanbikan.nookie.feature.tutorial.navigation.welcomeScreen
import kotlinx.coroutines.flow.Flow

const val phoneGraphRoute = "phone_graph"

const val phoneScreenRoute = "phone_screen_route"


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
        phoneScreen(
            navigateToTutorial = navController::navigateToTutorial,
            navigateToTodo = navController::navigateToTodo,
        )

        welcomeScreen(
            navigateToAddUser = navController::navigateToAddUser,
        )
        addUserScreen(
            navigateUp = navController::navigateUp,
            navigateToTutorial = navController::navigateToTutorial
        )
        tutorialScreen(
            navigateToPhone = navController::navigateToPhone,
        )
        todoScreen(
            navigateToAddUser = navController::navigateToAddUser,
            navigateToPhone = navController::navigateToPhone,
        )
    }
}

fun NavGraphBuilder.phoneScreen(
    navigateToTutorial: () -> Unit,
    navigateToTodo: () -> Unit,
) {
    composable(
        route = phoneScreenRoute,
    ) {
        PhoneScreen(
            navigateToTutorial = navigateToTutorial,
            navigateToTodo = navigateToTodo,
        )
    }
}

fun NavController.navigateToPhone() {
    navigate(phoneScreenRoute)
}