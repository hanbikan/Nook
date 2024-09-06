package com.hanbikan.nook.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hanbikan.nook.MainViewModel
import com.hanbikan.nook.R
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.ui.TRANSITION_DURATION
import com.hanbikan.nook.feature.museum.navigation.museumGraph
import com.hanbikan.nook.feature.museum.navigation.museumGraphRoute
import com.hanbikan.nook.feature.museum.navigation.museumRoutes
import com.hanbikan.nook.feature.museum.navigation.navigateToMonthlyCollectible
import com.hanbikan.nook.feature.museum.navigation.navigateToRegisterCollectible
import com.hanbikan.nook.feature.profile.navigation.profileScreen
import com.hanbikan.nook.feature.profile.navigation.profileScreenRoute
import com.hanbikan.nook.feature.todo.navigation.navigateToTodo
import com.hanbikan.nook.feature.todo.navigation.navigateToTutorial
import com.hanbikan.nook.feature.todo.navigation.todoGraph
import com.hanbikan.nook.feature.todo.navigation.todoGraphRoute
import com.hanbikan.nook.feature.todo.navigation.todoRoutes

@Composable
fun MainScreen(
    navigateToAddUser: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Todo.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
            popEnterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
            popExitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
        ) {
            todoGraph(
                startDestination = viewModel.todoGraphRoute.value,
                navigateToTodo = navController::navigateToTodo,
                navigateToTutorial = navController::navigateToTutorial,
            )
            museumGraph(
                navigateToMonthlyCollectible = navController::navigateToMonthlyCollectible,
                navigateToRegisterCollectible = navController::navigateToRegisterCollectible,
                navigateUp = navController::navigateUp
            )
            profileScreen(
                navigateToAddUser = navigateToAddUser,
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Todo,
        BottomNavItem.Museum,
        BottomNavItem.Profile
    )

    NavigationBar(
        modifier = Modifier
            .height(64.dp)
            .shadow(8.dp, shape = RectangleShape),
        containerColor = NkTheme.colorScheme.background,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = if (item.route == todoGraphRoute) {
                todoRoutes.contains(currentRoute)
            } else if (item.route == museumGraphRoute) {
                museumRoutes.contains(currentRoute)
            } else {
                item.route == currentRoute
            }
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon(),
                        contentDescription = null,
                        tint = NkTheme.colorScheme.primary,
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = NkTheme.colorScheme.primaryContainer,
                )
            )
        }
    }
}

sealed class BottomNavItem(val icon: @Composable () -> ImageVector, val route: String) {
    object Todo : BottomNavItem({ ImageVector.vectorResource(id = R.drawable.baseline_checklist_24) }, todoGraphRoute)
    object Museum : BottomNavItem({ ImageVector.vectorResource(id = R.drawable.baseline_museum_24) }, museumGraphRoute)
    object Profile : BottomNavItem({ Icons.Filled.AccountCircle }, profileScreenRoute)
}