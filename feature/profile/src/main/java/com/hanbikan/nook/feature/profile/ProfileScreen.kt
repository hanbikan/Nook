package com.hanbikan.nook.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hanbikan.nook.core.designsystem.component.NkText

@Composable
fun ProfileScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NkText("Profile Screen")
    }
}