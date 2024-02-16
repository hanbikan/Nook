package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun TitleTextWithSpacer(
    title: String,
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    Column {
        Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingExtraSmall)
        ) {
            NkText(
                text = title,
                color = NkTheme.colorScheme.primaryContainer
            )
            content?.invoke(this)
        }
        Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
    }
}

@Composable
@Preview
fun TitleTextWithSpacerPreview() {
    TitleTextWithSpacer("진행도", {})
}