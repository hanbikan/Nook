package com.hanbikan.nook.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun WelcomeText(userName: String) {
    NkText(
        text = stringResource(id = R.string.welcome_text, userName),
        style = NkTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
    )
}