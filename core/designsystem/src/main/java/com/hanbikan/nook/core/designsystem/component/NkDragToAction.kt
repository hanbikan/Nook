package com.hanbikan.nook.core.designsystem.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.theme.Dimens
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NkDragToAction(
    dragActions: DragActions,
    dragThresholdsDp: Float = 72f,
    velocityThresholdDp: Float = 100f,
    positionalThresholdWeight: Float = 0.5f,
    margin: Dp = Dimens.SpacingSmall,
    content: @Composable (Modifier) -> Unit,
) {
    val anchoredDraggableState = createAnchoredDraggableState(
        dragThresholdsDp = dragThresholdsDp,
        velocityThresholdDp = velocityThresholdDp,
        positionalThresholdWeight = positionalThresholdWeight
    )
    val draggableModifier = Modifier
        .offset {
            IntOffset(
                anchoredDraggableState
                    .requireOffset()
                    .roundToInt(), 0
            )
        }
        .anchoredDraggable(anchoredDraggableState, Orientation.Horizontal)

    Layout(
        content = {
            content(draggableModifier)
            NkActionButton(
                action = dragActions.endAction,
                dragToCenter = { anchoredDraggableState.dragToCenter() },
                dragThresholdsDp = dragThresholdsDp,
                margin = margin,
            )
            NkActionButton(
                action = dragActions.startAction,
                dragToCenter = { anchoredDraggableState.dragToCenter() },
                dragThresholdsDp = dragThresholdsDp,
                margin = margin,
            )
        }
    ) { measurableList, constraints ->
        // NkActionButton의 높이를 content의 높이로 맞춥니다.
        val contentPlaceable = measurableList[0].measure(constraints)
        val contentHeightConstraints = constraints.copy(
            minHeight = contentPlaceable.height,
            maxHeight = contentPlaceable.height
        )
        val endActionPlaceable = measurableList[1].measure(contentHeightConstraints)
        val startActionPlaceable = measurableList[2].measure(contentHeightConstraints)

        val startActionX = constraints.maxWidth - startActionPlaceable.width // End에 버튼 배치

        layout(constraints.maxWidth, contentPlaceable.height) {
            endActionPlaceable.place(0, 0)
            startActionPlaceable.place(startActionX, 0)
            contentPlaceable.placeRelative(0, 0)
        }
    }
}

@Composable
fun NkActionButton(
    action: DragAction?,
    dragToCenter: () -> Unit,
    dragThresholdsDp: Float,
    margin: Dp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    if (action != null) {
        Box(
            modifier = Modifier
                .width(dragThresholdsDp.dp - margin)
                .background(
                    action.backgroundColor,
                    RoundedCornerShape(Dimens.SpacingMedium)
                )
                .clickable(
                    onClick = {
                        dragToCenter()
                        action.onClick()
                    },
                    interactionSource = interactionSource,
                    indication = null
                ),
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = action.iconImageVector,
                contentDescription = null,
                tint = action.iconTint
            )
        }
    } else {
        Box {}
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun createAnchoredDraggableState(
    dragThresholdsDp: Float,
    velocityThresholdDp: Float,
    positionalThresholdWeight: Float,
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
    val onClick: () -> Unit,
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
fun <T> AnchoredDraggableState<T>.dragToCenter() {
    dispatchRawDelta(-offset)
}