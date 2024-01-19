package com.hanbikan.nookie.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hanbikan.nookie.core.designsystem.theme.Dimens
import com.hanbikan.nookie.core.designsystem.theme.NkTheme

@Composable
fun TitleTextWithSpacer(
    title: String,
) {
    Column {
        NkText(
            text = title,
            color = NkTheme.colorScheme.primaryContainer,
        )
        Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
    }
}