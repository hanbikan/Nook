package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun NkTag(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = NkTheme.colorScheme.primary,
                shape = RoundedCornerShape(Dimens.SpacingSmall)
            )
            .padding(Dimens.SpacingExtraSmall),
    ) {
        NkText(
            text = text,
            color = Color.White,
            style = NkTheme.typography.labelSmall
        )
    }
}

@Composable
@Preview
fun NkTagPreview() {
    NkTag(text = "Text")
}