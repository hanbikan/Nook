package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun NkInfoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = Dimens.IconExtraSmall,
    tint: Color = NkTheme.colorScheme.primaryContainer,
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    Icon(
        modifier = modifier
            .size(size)
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = size
                ),
            ),
        imageVector = Icons.Default.Info,
        contentDescription = null,
        tint = tint
    )
}

@Composable
@Preview
fun NkInfoButtonPreview() {
    NkInfoButton({})
}