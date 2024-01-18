package com.hanbikan.nooknook.feature.tutorial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nooknook.feature.tutorial.AddUserScreen
import com.hanbikan.nooknook.feature.tutorial.WelcomeScreen

const val welcomeScreenRoute = "welcome_screen_route"
const val addUserScreenRoute = "add_user_screen_route"

fun NavGraphBuilder.welcomeScreen(
    navigateToAddUser: () -> Unit,
) {
    composable(
        route = welcomeScreenRoute,
    ) {
        WelcomeScreen(
            navigateToAddUser = navigateToAddUser,
        )
    }
}

fun NavGraphBuilder.addUserScreen() {
    composable(
        route = addUserScreenRoute,
    ) {
        AddUserScreen()
    }
}

fun NavController.navigateToWelcome() {
    navigate(welcomeScreenRoute)
}

fun NavController.navigateToAddUser() {
    navigate(addUserScreenRoute)
}