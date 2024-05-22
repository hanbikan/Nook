package com.hanbikan.nook.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import kotlinx.coroutines.delay

@Composable
fun NkAnimatedCircularProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    size: Dp = 200.dp,
    description: String = "",
    animationDurationMillis: Int = 750,
    animationDelayMillis: Long = 150L,
) {
    var progressToShow by remember { mutableFloatStateOf(0f) }
    var isFirstToModify by remember { mutableStateOf(true) }
    val animatedProgress by animateFloatAsState(
        targetValue = progressToShow,
        animationSpec = tween(durationMillis = animationDurationMillis),
        label = "NkCircularProgress"
    )
    val progressAsPercent = "${(animatedProgress * 100).toInt()}%"

    LaunchedEffect(progress) {
        if (isFirstToModify) {
            delay(animationDelayMillis)
            isFirstToModify = false
        }
        progressToShow = progress
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            progress = { animatedProgress },
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