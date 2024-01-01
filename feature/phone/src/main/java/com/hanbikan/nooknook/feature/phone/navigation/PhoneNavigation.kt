package com.hanbikan.nooknook.feature.phone.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hanbikan.nooknook.feature.tutorial.TutorialScreen

const val phoneGraphRoute = "phone_graph"

private const val tutorialScreenRoute = "tutorial_screen_route"

fun NavGraphBuilder.phoneGraph() {
    navigation(
        route = phoneGraphRoute,
        startDestination = tutorialScreenRoute,
    ) {
        composable(
            route = tutorialScreenRoute,
        ) {
            TutorialScreen()
        }
    }
}