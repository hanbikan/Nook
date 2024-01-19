package com.hanbikan.nookie.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.linearColor,
    trackColor: Color = ProgressIndicatorDefaults.linearTrackColor
) {
    val animateFloat by animateFloatAsState(
        targetValue = progress,
        label = "AnimatedLinearProgressIndicator",
    )
    LinearProgressIndicator(
        progress = animateFloat,
        modifier = modifier,
        color = color,
        trackColor = trackColor
    )
}