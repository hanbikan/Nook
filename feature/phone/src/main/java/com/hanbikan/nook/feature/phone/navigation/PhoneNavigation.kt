package com.hanbikan.nook.feature.phone.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hanbikan.nook.core.ui.NkApp
import com.hanbikan.nook.feature.phone.PhoneScreen
import com.hanbikan.nook.feature.profile.navigation.navigateToProfile
import com.hanbikan.nook.feature.profile.navigation.profileScreen
import com.hanbikan.nook.feature.profile.navigation.profileScreenRoute
import com.hanbikan.nook.feature.todo.navigation.navigateToTodo
import com.hanbikan.nook.feature.todo.navigation.todoScreen
import com.hanbikan.nook.feature.todo.navigation.todoScreenRoute
import com.hanbikan.nook.feature.tutorial.navigation.addUserScreen
import com.hanbikan.nook.feature.tutorial.navigation.navigateToAddUser
import com.hanbikan.nook.feature.tutorial.navigation.navigateToTutorial
import com.hanbikan.nook.feature.tutorial.navigation.tutorialScreen
import com.hanbikan.nook.feature.tutorial.navigation.tutorialScreenRoute
import com.hanbikan.nook.feature.tutorial.navigation.welcomeScreen

const val phoneGraphRoute = "phone_graph"

const val phoneScreenRoute = "phone_screen_route"


fun NavGraphBuilder.phoneGraph(
    navController: NavHostController,
    startDestination: String,
) {
    navigation(
        route = phoneGraphRoute,
        startDestination = startDestination,
    ) {
        welcomeScreen(
            navigateToAddUser = navController::navigateToAddUser,
        )
        addUserScreen(
            navigateUp = navController::navigateUp,
            navigateToTutorial = navController::navigateToTutorial
        )

        phoneScreen(
            navigateToProfile = navController::navigateToProfile,
            navigateToTutorial = navController::navigateToTutorial,
            navigateToTodo = navController::navigateToTodo,
            navigateToAddUser = navController::navigateToAddUser,
        )

        profileScreen(
            navigateToAddUser = navController::navigateToAddUser,
            navigateToPhone = navController::navigateToPhone,
        )
        tutorialScreen(
            navigateToAddUser = navController::navigateToAddUser,
            navigateToPhone = navController::navigateToPhone,
            navigateToTodo = navController::navigateToTodo,
        )
        todoScreen(
            navigateToAddUser = navController::navigateToAddUser,
            navigateToPhone = navController::navigateToPhone,
        )
    }
}

fun NavGraphBuilder.phoneScreen(
    navigateToProfile: () -> Unit,
    navigateToTutorial: () -> Unit,
    navigateToTodo: () -> Unit,
    navigateToAddUser: () -> Unit,
) {
    composable(
        route = phoneScreenRoute,
    ) {
        PhoneScreen(
            nkApps = listOf(
                NkApp.PROFILE.toNkAppWithNavigation(profileScreenRoute, navigateToProfile),
                NkApp.TUTORIAL.toNkAppWithNavigation(tutorialScreenRoute, navigateToTutorial),
                NkApp.TODO.toNkAppWithNavigation(todoScreenRoute, navigateToTodo),
            ),
            navigateToAddUser = navigateToAddUser,
        )
    }
}

fun NavController.navigateToPhone() {
    navigate(phoneScreenRoute)
}