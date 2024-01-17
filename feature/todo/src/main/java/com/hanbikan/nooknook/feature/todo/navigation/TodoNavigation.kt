package com.hanbikan.nooknook.feature.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nooknook.feature.todo.TodoScreen

const val todoScreenRoute = "todo_screen_route"

fun NavGraphBuilder.todoScreen() {
    composable(
        route = todoScreenRoute,
    ) {
        TodoScreen()
    }
}

fun NavController.navigateToTodo() {
    navigate(todoScreenRoute)
}