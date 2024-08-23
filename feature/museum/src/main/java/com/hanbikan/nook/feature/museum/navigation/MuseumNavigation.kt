package com.hanbikan.nook.feature.museum.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
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

fun NavGraphBuilder.museumGraph(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    navigateToMonthlyCollectible: (CollectibleSequence) -> Unit,
    navigateToRegisterCollectible: () -> Unit,
    navigateUp: () -> Unit,
) {
    navigation(
        route = museumGraphRoute,
        startDestination = museumScreenRoute,
    ) {
        composable(
            route = museumScreenRoute,
        ) {
            MuseumScreen(
                navigateToAddUser = navigateToAddUser,
                navigateToPhone = navigateToPhone,
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
            RegisterCollectibleScreen(navigateUp = navigateUp)
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