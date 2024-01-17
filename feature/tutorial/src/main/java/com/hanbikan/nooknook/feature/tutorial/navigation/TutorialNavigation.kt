package com.hanbikan.nooknook.feature.tutorial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nooknook.feature.tutorial.TutorialScreen

const val tutorialScreenRoute = "tutorial_screen_route"

fun NavGraphBuilder.tutorialScreen() {
    composable(
        route = tutorialScreenRoute,
    ) {
        TutorialScreen()
    }
}

fun NavController.navigateToTutorial() {
    navigate(tutorialScreenRoute)
}