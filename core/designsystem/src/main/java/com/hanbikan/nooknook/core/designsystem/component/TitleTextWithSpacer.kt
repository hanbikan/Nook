package com.hanbikan.nooknook.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@Composable
fun TitleTextWithSpacer(
    title: String,
) {
    Column {
        NnText(
            text = title,
            color = NnTheme.colorScheme.primaryContainer,
        )
        Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
    }
}