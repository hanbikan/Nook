package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun NkCircularProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    size: Dp = 200.dp,
    description: String = "",
) {
    val progressAsPercent = "${(progress * 100).toInt()}%"

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(size),
            strokeWidth = Dimens.SpacingMedium,
            color = NkTheme.colorScheme.primary,
            trackColor = NkTheme.colorScheme.primaryContainer,
            strokeCap = StrokeCap.Round,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            NkText(
                text = progressAsPercent,
                style = NkTheme.typography.headlineMedium,
            )
            if (description.isNotEmpty()) {
                NkText(
                    text = description,
                    color = NkTheme.colorScheme.primaryContainer,
                    style = NkTheme.typography.bodyMedium,
                )
            }
        }
    }
}