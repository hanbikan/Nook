package com.hanbikan.nookie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.hanbikan.nookie.feature.phone.navigation.phoneGraph
import com.hanbikan.nookie.feature.phone.navigation.phoneGraphRoute

@Composable
fun NnNavHost(
    appState: NnAppState,
) {
    NavHost(
        navController = appState.navController,
        startDestination = phoneGraphRoute,
    ) {
        phoneGraph(
            hasAnyUsers = appState.hasAnyUsers,
            navController = appState.navController,
        )
    }
}