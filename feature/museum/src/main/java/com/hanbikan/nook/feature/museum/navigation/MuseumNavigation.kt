package com.hanbikan.nook.feature.museum.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hanbikan.nook.feature.museum.MonthlyCollectibleScreen
import com.hanbikan.nook.feature.museum.MuseumScreen

const val museumGraphRoute = "museum_graph_route"

const val museumScreenRoute = "museum_screen_route"
const val monthlyCollectibleScreenRoute = "monthly_collectible_screen_route"

fun NavGraphBuilder.museumGraph(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    navigateToMonthlyCollectible: () -> Unit,
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
            )
        }

        composable(
            route = monthlyCollectibleScreenRoute,
        ) {
            MonthlyCollectibleScreen(
                navigateUp = navigateUp,
            )
        }
    }
}

fun NavController.navigateToMuseum() {
    navigate(museumGraphRoute)
}

fun NavController.navigateToMonthlyCollectible() {
    navigate(monthlyCollectibleScreenRoute)
}