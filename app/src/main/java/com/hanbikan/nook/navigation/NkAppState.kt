package com.hanbikan.nook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberNkAppState(
    startDestination: String,
    navController: NavHostController = rememberNavController(),
): NkAppState {
    return remember(
        startDestination,
        navController,
    ) {
        NkAppState(
            navController,
            startDestination
        )
    }
}

class NkAppState(
    val navController: NavHostController,
    val startDestination: String,
)