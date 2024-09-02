package com.hanbikan.nook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

@Composable
fun NkNavHost(
    appState: NkAppState,
) {
    NavHost(
        navController = appState.navController,
        startDestination = mainGraphRoute,
    ) {
        mainGraph(
            navController = appState.navController,
            startDestination = appState.startDestination,
        )
    }
}