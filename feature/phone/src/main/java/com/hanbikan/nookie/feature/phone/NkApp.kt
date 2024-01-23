package com.hanbikan.nookie.feature.phone

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

/**
 * Defines contents of Nookie app in which [PhoneScreen] holds.
 */
data class NkApp(
    val name: String,
    val painter: Painter,
    val navigate: () -> Unit,
)

@Composable
fun createTutorialNkApp(navigate: () -> Unit) = NkApp(
    name = stringResource(id = R.string.tutorial_nk_app),
    painter = painterResource(id = R.drawable.nookie),
    navigate = navigate
)

@Composable
fun createTodoNkApp(navigate: () -> Unit) = NkApp(
    name = stringResource(id = R.string.todo_nk_app),
    painter = painterResource(id = R.drawable.nookie),
    navigate = navigate
)