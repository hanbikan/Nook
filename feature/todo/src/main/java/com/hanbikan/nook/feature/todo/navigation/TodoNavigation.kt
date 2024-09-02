package com.hanbikan.nook.feature.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hanbikan.nook.feature.todo.TodoScreen
import com.hanbikan.nook.feature.todo.TutorialScreen

const val todoGraphRoute = "todo_graph_route"

const val todoScreenRoute = "todo_screen_route"
const val tutorialScreenRoute = "tutorial_screen_route"

val todoRoutes = listOf(todoScreenRoute, tutorialScreenRoute)

fun NavGraphBuilder.todoScreen(
    navigateToTutorial: () -> Unit,
    navigateToTodo: () -> Unit,
) {
    navigation(
        route = todoGraphRoute,
        startDestination = tutorialScreenRoute,
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