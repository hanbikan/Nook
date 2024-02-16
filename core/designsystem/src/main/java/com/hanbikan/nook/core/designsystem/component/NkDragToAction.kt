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

/**
 * [draggableContent]에 드래그를 적용하고 드래그 시 남는 공간에 [DragAction]을 표시하는 컴포넌트입니다.
 * @param dragActions [DragActions]
 * @param dragThresholdsDp 드래그되는 최대 범위입니다.
 * @param velocityThresholdDp 1초에 [velocityThresholdDp] 이상 움직이면 드래그 처리가 됩니다.(스냅)
 * @param positionalThresholdWeight [dragThresholdsDp] * [positionalThresholdWeight] 이상 움직이면 드래그
 * 처리가 됩니다.
 * @param margin 드래그 되었을 때 [DragAction] 버튼과 [draggableContent] 사이의 간격입니다.
 * @param draggableContent 드래그가 적용될 메인 컨텐츠입니다.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NkDragToAction(
    dragActions: DragActions,
    dragThresholdsDp: Float = 72f,
    velocityThresholdDp: Float = 100f,
    positionalThresholdWeight: Float = 0.5f,
    margin: Dp = Dimens.SpacingSmall,
    draggableContent: @Composable (Modifier) -> Unit,
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
            draggableContent(draggableModifier)
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

/**
 * [NkDragToAction] 양쪽에 나타나는 액션 버튼입니다. 클릭되었을 때 [dragToCenter], [DragAction.onClick]를 호출합니다.
 */
@Composable
fun NkActionButton(
    action: DragAction,
    dragToCenter: () -> Unit,
    dragThresholdsDp: Float,
    margin: Dp,
) {
    val interactionSource = remember { MutableInteractionSource() }

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

/**
 * @param startAction 좌측으로 드래그했을 때 우측에 표시되는 [DragAction]
 * @param endAction 우측으로 드래그했을 때 좌측에 표시되는 [DragAction]
 */
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

/**
 * [NkActionButton]의 UI에 필요한 데이터 클래스
 */
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