package com.hanbikan.nook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.hanbikan.nook.feature.phone.navigation.phoneGraph
import com.hanbikan.nook.feature.phone.navigation.phoneGraphRoute

@Composable
fun NkNavHost(
    appState: NkAppState,
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