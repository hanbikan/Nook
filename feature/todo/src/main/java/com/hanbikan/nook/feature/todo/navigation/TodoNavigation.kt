package com.hanbikan.nook.feature.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hanbikan.nook.feature.todo.TodoScreen

const val todoScreenRoute = "todo_screen_route"

fun NavGraphBuilder.todoScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
) {
    composable(
        route = todoScreenRoute,
    ) {
        TodoScreen(
            navigateToAddUser = navigateToAddUser,
            navigateToPhone = navigateToPhone,
        )
    }
}

fun NavController.navigateToTodo() {
    navigate(todoScreenRoute)
}