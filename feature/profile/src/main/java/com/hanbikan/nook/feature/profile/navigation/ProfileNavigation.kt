package com.hanbikan.nook.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nook.feature.profile.ProfileScreen

const val profileScreenRoute = "profile_screen_route"

fun NavGraphBuilder.profileScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
) {
    composable(
        route = profileScreenRoute,
    ) {
        ProfileScreen(
            navigateToAddUser = navigateToAddUser,
            navigateToPhone = navigateToPhone,
        )
    }
}

fun NavController.navigateToProfile() {
    navigate(profileScreenRoute)
}