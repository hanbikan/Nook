package com.hanbikan.nook.core.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

enum class DragValue { Start, Center, End }

data class DragActions(
    val startAction: DragAction,
    val endAction: DragAction,
) {
    companion object {
        fun withSameActions(action: DragAction) = DragActions(
            startAction = action,
            endAction = action
        )
    }
}

data class DragAction(
    val backgroundColor: Color,
    val iconImageVector: ImageVector,
    val iconTint: Color,
    val onClick: () -> Unit
) {
    companion object {
        fun deleteAction(onClick: () -> Unit) = DragAction(
            backgroundColor = Color.Red,
            iconImageVector = Icons.Default.Delete,
            iconTint = Color.White,
            onClick = onClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun createAnchoredDraggableState(
    dragThresholdsDp: Float,
    velocityThresholdDp: Float,
    positionalThresholdWeight: Float
): AnchoredDraggableState<DragValue> {
    val density = LocalDensity.current
    val dragThresholdsPx = with(density) { dragThresholdsDp.dp.toPx() }
    return remember {
        AnchoredDraggableState(
            initialValue = DragValue.Center,
            positionalThreshold = { distance: Float -> distance * positionalThresholdWeight },
            velocityThreshold = { with(density) { velocityThresholdDp.dp.toPx() } },
            animationSpec = tween()
        ).apply {
            updateAnchors(
                DraggableAnchors {
                    DragValue.Start at -dragThresholdsPx
                    DragValue.Center at 0f
                    DragValue.End at dragThresholdsPx
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun <T> AnchoredDraggableState<T>.dragToCenter() {
    dispatchRawDelta(-offset)
}