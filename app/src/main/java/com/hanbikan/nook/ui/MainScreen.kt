package com.hanbikan.nook.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.feature.museum.navigation.museumGraph
import com.hanbikan.nook.feature.museum.navigation.museumGraphRoute
import com.hanbikan.nook.feature.museum.navigation.museumRoutes
import com.hanbikan.nook.feature.museum.navigation.navigateToMonthlyCollectible
import com.hanbikan.nook.feature.museum.navigation.navigateToRegisterCollectible
import com.hanbikan.nook.feature.profile.navigation.profileScreen
import com.hanbikan.nook.feature.profile.navigation.profileScreenRoute
import com.hanbikan.nook.feature.todo.navigation.todoScreen
import com.hanbikan.nook.feature.todo.navigation.todoScreenRoute
import com.hanbikan.nook.feature.tutorial.navigation.navigateToAddUser

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Todo.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            todoScreen(
                navigateToAddUser = navController::navigateToAddUser
            )
            museumGraph(
                navigateToAddUser = navController::navigateToAddUser,
                navigateToMonthlyCollectible = navController::navigateToMonthlyCollectible,
                navigateToRegisterCollectible = navController::navigateToRegisterCollectible,
                navigateUp = navController::navigateUp
            )
            profileScreen(
                navigateToAddUser = navController::navigateToAddUser,
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
        modifier = Modifier.height(64.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = when (item.route) {
                todoScreenRoute -> item.route == currentRoute
                profileScreenRoute -> item.route == currentRoute
                else -> museumRoutes.contains(currentRoute)
            }
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
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

sealed class BottomNavItem(val icon: ImageVector, val route: String) {
    object Todo : BottomNavItem(Icons.Filled.Home, todoScreenRoute)
    object Museum : BottomNavItem(Icons.Filled.Search, museumGraphRoute)
    object Profile : BottomNavItem(Icons.Filled.AccountCircle, profileScreenRoute)
}