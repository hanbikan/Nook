package com.hanbikan.nook.ui

import androidx.compose.runtime.Composable
import com.hanbikan.nook.core.designsystem.component.NkBackground
import com.hanbikan.nook.navigation.NkAppState
import com.hanbikan.nook.navigation.NkNavHost
import com.hanbikan.nook.navigation.rememberNkAppState

@Composable
fun NkApp(
    startDestination: String,
    appState: NkAppState = rememberNkAppState(
        startDestination = startDestination,
    ),
) {
    NkBackground {
        NkNavHost(appState = appState)
    }
}