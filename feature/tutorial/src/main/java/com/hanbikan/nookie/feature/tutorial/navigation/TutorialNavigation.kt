package com.hanbikan.nookie.feature.tutorial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nookie.feature.tutorial.AddUserScreen
import com.hanbikan.nookie.feature.tutorial.TutorialScreen
import com.hanbikan.nookie.feature.tutorial.WelcomeScreen

const val welcomeScreenRoute = "welcome_screen_route"
const val addUserScreenRoute = "add_user_screen_route"
const val tutorialScreenRoute = "tutorial_screen_route"

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

fun NavGraphBuilder.addUserScreen(
    navigateUp: () -> Unit,
    navigateToTutorial: () -> Unit,
) {
    composable(
        route = addUserScreenRoute,
    ) {
        AddUserScreen(
            navigateUp = navigateUp,
            navigateToTutorial = navigateToTutorial
        )
    }
}

fun NavGraphBuilder.tutorialScreen(
    navigateToPhone: () -> Unit,
) {
    composable(
        route = tutorialScreenRoute,
    ) {
        TutorialScreen(
            navigateToPhone = navigateToPhone,
        )
    }
}

fun NavController.navigateToWelcome() {
    navigate(welcomeScreenRoute)
}

fun NavController.navigateToAddUser() {
    navigate(addUserScreenRoute)
}

fun NavController.navigateToTutorial() {
    navigate(tutorialScreenRoute)
}