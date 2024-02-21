package com.hanbikan.nook.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

/**
 * Defines contents of Nook app in which the phone screen holds.
 */
data class NkApp(
    val name: String,
    val painter: Painter,
) {
    fun toNkAppWithNavigation(
        route: String,
        navigate: () -> Unit,
    ): NkAppWithNavigation {
        return NkAppWithNavigation(name, painter, route, navigate)
    }

    companion object {
        val PROFILE: NkApp
            @Composable get() {
                return NkApp(
                    name = stringResource(id = R.string.profile_nk_app),
                    painter = painterResource(id = R.drawable.profile)
                )
            }
        
        val TUTORIAL: NkApp
            @Composable get() {
                return NkApp(
                    name = stringResource(id = R.string.tutorial_nk_app),
                    painter = painterResource(id = R.drawable.tutorial)
                )
            }

        val TODO: NkApp
            @Composable get() {
                return NkApp(
                    name = stringResource(id = R.string.todo_nk_app),
                    painter = painterResource(id = R.drawable.todo)
                )
            }
    }
}

data class NkAppWithNavigation(
    val name: String,
    val painter: Painter,
    val route: String,
    val navigate: () -> Unit,
)