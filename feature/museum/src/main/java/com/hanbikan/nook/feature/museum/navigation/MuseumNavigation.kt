package com.hanbikan.nook.feature.museum.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.hanbikan.nook.core.ui.TRANSITION_DURATION
import com.hanbikan.nook.feature.museum.CollectibleScreen
import com.hanbikan.nook.feature.museum.MuseumScreen
import com.hanbikan.nook.feature.museum.RegisterCollectibleScreen
import com.hanbikan.nook.feature.museum.model.CollectibleSequence

const val museumGraphRoute = "museum_graph_route"

const val museumScreenRoute = "museum_screen_route"

const val MONTHLY_COLLECTIBLE_SCREEN_ROUTE_BASE = "monthly_collectible_screen_route"
const val COLLECTIBLE_SEQUENCE_INDEX = "collectibleSequenceIndex"
const val monthlyCollectibleScreenRoute = "$MONTHLY_COLLECTIBLE_SCREEN_ROUTE_BASE/{$COLLECTIBLE_SEQUENCE_INDEX}"

const val registerCollectibleScreenRoute = "register_collectible_screen_route"

val museumRoutes = listOf(museumScreenRoute, monthlyCollectibleScreenRoute, registerCollectibleScreenRoute)

fun NavGraphBuilder.museumGraph(
    navigateToMonthlyCollectible: (CollectibleSequence) -> Unit,
    navigateToRegisterCollectible: () -> Unit,
    navigateUp: () -> Unit,
) {
    navigation(
        route = museumGraphRoute,
        startDestination = museumScreenRoute,
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
    ) {
        composable(
            route = museumScreenRoute,
        ) {
            MuseumScreen(
                navigateToMonthlyCollectible = navigateToMonthlyCollectible,
                navigateToRegisterCollectible = navigateToRegisterCollectible,
            )
        }

        composable(
            route = monthlyCollectibleScreenRoute,
            arguments = listOf(
                navArgument(COLLECTIBLE_SEQUENCE_INDEX) { type = NavType.IntType }
            )
        ) {
            CollectibleScreen(
                navigateUp = navigateUp,
            )
        }

        composable(
            route = registerCollectibleScreenRoute,
        ) {
            RegisterCollectibleScreen(
                navigateUp = navigateUp,
            )
        }
    }
}

fun NavController.navigateToMuseum() {
    navigate(museumGraphRoute)
}

fun NavController.navigateToMonthlyCollectible(collectibleSequence: CollectibleSequence) {
    navigate("$MONTHLY_COLLECTIBLE_SCREEN_ROUTE_BASE/${collectibleSequence.ordinal}")
}

fun NavController.navigateToRegisterCollectible() {
    navigate(registerCollectibleScreenRoute)
}