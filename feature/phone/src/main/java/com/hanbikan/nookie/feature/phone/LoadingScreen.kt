package com.hanbikan.nookie.feature.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hanbikan.nookie.core.designsystem.theme.NnTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(
    hasAnyUsers: Flow<Boolean>,
    navigateToWelcome: () -> Unit,
    navigateToTodo: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            if (!hasAnyUsers.first()) {
                navigateToWelcome()
            } else {
                navigateToTodo()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NnTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

    }
}