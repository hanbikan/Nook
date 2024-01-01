package com.hanbikan.nooknook.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.hanbikan.nooknook.navigation.NnNavHost

@Composable
fun NnApp() {
    val navController = rememberNavController()
    NnNavHost(navController = navController)
}