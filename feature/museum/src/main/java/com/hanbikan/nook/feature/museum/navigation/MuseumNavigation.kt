package com.hanbikan.nook.feature.museum.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nook.feature.museum.MuseumScreen

const val museumScreenRoute = "museum_screen_route"

fun NavGraphBuilder.museumScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
) {
    composable(
        route = museumScreenRoute,
    ) {
        MuseumScreen(
            navigateToAddUser = navigateToAddUser,
            navigateToPhone = navigateToPhone,
        )
    }
}

fun NavController.navigateToMuseum() {
    navigate(museumScreenRoute)
}