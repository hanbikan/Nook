package com.hanbikan.nook.feature.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nook.feature.todo.TodoScreen

const val todoScreenRoute = "todo_screen_route"
const val tutorialScreenRoute = "tutorial_screen_route"

fun NavGraphBuilder.todoScreen(
    navigateToTutorial: () -> Unit,
) {
    composable(
        route = todoScreenRoute,
    ) {
        TodoScreen(
            navigateToTutorial = navigateToTutorial,
        )
    }
}

fun NavController.navigateToTodo() {
    navigate(todoScreenRoute)
}

fun NavGraphBuilder.tutorialScreen(
    navigateToTodo: () -> Unit,
) {
    composable(
        route = tutorialScreenRoute,
    ) {
        com.hanbikan.nook.feature.todo.TutorialScreen(
            navigateToTodo = navigateToTodo,
        )
    }
}

fun NavController.navigateToTutorial() {
    navigate(tutorialScreenRoute)
}