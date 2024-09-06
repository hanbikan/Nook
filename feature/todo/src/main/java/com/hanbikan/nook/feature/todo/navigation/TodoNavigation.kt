package com.hanbikan.nook.feature.todo.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hanbikan.nook.core.ui.TRANSITION_DURATION
import com.hanbikan.nook.feature.todo.TodoScreen
import com.hanbikan.nook.feature.todo.TutorialScreen

const val todoGraphRoute = "todo_graph_route"

const val todoScreenRoute = "todo_screen_route"
const val tutorialScreenRoute = "tutorial_screen_route"

val todoRoutes = listOf(todoScreenRoute, tutorialScreenRoute)

fun NavGraphBuilder.todoGraph(
    startDestination: String,
    navigateToTutorial: () -> Unit,
    navigateToTodo: () -> Unit,
) {
    navigation(
        route = todoGraphRoute,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
        popExitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
    ) {
        composable(
            route = tutorialScreenRoute,
        ) {
            TutorialScreen(
                navigateToTodo = navigateToTodo,
            )
        }

        composable(
            route = todoScreenRoute,
        ) {
            TodoScreen(
                navigateToTutorial = navigateToTutorial,
            )
        }
    }
}

fun NavController.navigateToTodo() {
    navigate(todoScreenRoute)
}

fun NavController.navigateToTutorial() {
    navigate(tutorialScreenRoute)
}