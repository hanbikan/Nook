package com.hanbikan.nookie.feature.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nookie.core.designsystem.theme.NkTheme

@Composable
fun PhoneScreen(
    navigateToTutorial: () -> Unit,
    navigateToTodo: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NkTheme.colorScheme.background),
    ) {

    }
}

@Composable
@Preview
fun PhoneScreenPreview() {
    PhoneScreen({}, {})
}