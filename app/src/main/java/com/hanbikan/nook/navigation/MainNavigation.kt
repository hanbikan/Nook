package com.hanbikan.nook.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hanbikan.nook.core.ui.TRANSITION_DURATION
import com.hanbikan.nook.feature.tutorial.navigation.addUserScreen
import com.hanbikan.nook.feature.tutorial.navigation.navigateToAddUser
import com.hanbikan.nook.feature.tutorial.navigation.welcomeScreen
import com.hanbikan.nook.ui.MainScreen

const val mainGraphRoute = "main_graph"
const val mainScreenRoute = "main_screen_route"

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    startDestination: String,
) {
    navigation(
        route = mainGraphRoute,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
    ) {
        welcomeScreen(
            navigateToAddUser = navController::navigateToAddUser,
        )
        addUserScreen(
            navigateUp = navController::navigateUp,
            navigateToMain = navController::navigateToMainScreen,
        )
        composable(
            route = mainScreenRoute
        ) {
            MainScreen(
                navigateToAddUser = navController::navigateToAddUser,
            )
        }
    }
}

fun NavController.navigateToMainScreen() {
    navigate(mainScreenRoute) {
        popUpTo(0) { inclusive = true }
    }
}