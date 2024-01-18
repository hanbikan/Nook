package com.hanbikan.nooknook.feature.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@Composable
fun AddUserScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NnTheme.colorScheme.background)
            .padding(Dimens.SideMargin),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

    }
}