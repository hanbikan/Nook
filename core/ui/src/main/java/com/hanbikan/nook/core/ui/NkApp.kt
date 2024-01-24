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
    fun toNkAppWithNavigation(navigate: () -> Unit): NkAppWithNavigation {
        return NkAppWithNavigation(name, painter, navigate)
    }

    companion object {
        val TUTORIAL: NkApp
            @Composable get() {
                return NkApp(
                    name = stringResource(id = R.string.tutorial_nk_app),
                    painter = painterResource(id = R.drawable.tutorial),
                )
            }

        val TODO: NkApp
            @Composable get() {
                return NkApp(
                    name = stringResource(id = R.string.todo_nk_app),
                    painter = painterResource(id = R.drawable.todo),
                )
            }
    }
}

data class NkAppWithNavigation(
    val name: String,
    val painter: Painter,
    val navigate: () -> Unit,
)