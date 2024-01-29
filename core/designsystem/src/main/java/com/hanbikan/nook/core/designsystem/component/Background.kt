package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun NkBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        color = NkTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        content()
    }
}