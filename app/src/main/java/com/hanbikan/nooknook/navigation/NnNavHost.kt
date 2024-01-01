package com.hanbikan.nooknook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hanbikan.nooknook.feature.phone.navigation.phoneGraph
import com.hanbikan.nooknook.feature.phone.navigation.phoneGraphRoute

@Composable
fun NnNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = phoneGraphRoute,
    ) {
        phoneGraph()
    }
}