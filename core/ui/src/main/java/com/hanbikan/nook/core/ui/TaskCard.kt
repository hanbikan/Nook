package com.hanbikan.nook.core.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.component.NkInfoButton
import com.hanbikan.nook.core.designsystem.component.NkTag
import com.hanbikan.nook.core.designsystem.component.NkTextWithContentAfter
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Completable
import com.hanbikan.nook.core.domain.model.TutorialTask
import kotlin.math.roundToInt

private const val dragThresholdsDp = 64f
private const val velocityThresholdDp = 100f
private const val positionalThresholdWeight = 0.5f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    completable: Completable,
    tag: String? = null,
    onClickCheckbox: () -> Unit,
    onLongClickTask: (() -> Unit)? = null,
    onClickInfo: (() -> Unit)? = null,
    endAction: TaskCardAction? = null,
    startAction: TaskCardAction? = null,
) {
    val density = LocalDensity.current
    val dragThresholdsPx = with(density) { dragThresholdsDp.dp.toPx() }
    val state = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Center,
            positionalThreshold = { distance: Float -> distance * positionalThresholdWeight },
            velocityThreshold = { with(density) { velocityThresholdDp.dp.toPx() } },
            animationSpec = tween()
        ).apply {
            updateAnchors(
                DraggableAnchors {
                    if (startAction != null) DragValue.Start at -dragThresholdsPx
                    DragValue.Center at 0f
                    if (endAction != null) DragValue.End at dragThresholdsPx
                }
            )
        }
    }
    val dragToCenter: () -> Unit = {
        state.dispatchRawDelta(-state.offset)
    }

    Column {
        Box {
            Layout(
                content = {
                    TaskCardContent(
                        completable = completable,
                        tag = tag,
                        onClickCheckbox = onClickCheckbox,
                        onLongClickTask = onLongClickTask,
                        onClickInfo = onClickInfo,
                        state = state,
                    )
                    TaskCardActionButton(
                        action = endAction,
                        dragToCenter = dragToCenter
                    )
                    TaskCardActionButton(
                        action = startAction,
                        dragToCenter = dragToCenter
                    )
                }
            ) { measurableList, constraints ->
                val contentPlaceable = measurableList[0].measure(constraints)
                val contentHeightConstraints = constraints.copy(minHeight = contentPlaceable.height, maxHeight = contentPlaceable.height)

                val endActionPlaceable = measurableList[1].measure(contentHeightConstraints)
                val startActionPlaceable = measurableList[2].measure(contentHeightConstraints)
                val startActionX = constraints.maxWidth - startActionPlaceable.width

                layout(constraints.maxWidth, contentPlaceable.height) {
                    endActionPlaceable.place(0, 0)
                    startActionPlaceable.place(startActionX, 0)
                    contentPlaceable.placeRelative(0, 0)
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCardContent(
    completable: Completable,
    tag: String? = null,
    onClickCheckbox: () -> Unit,
    onLongClickTask: (() -> Unit)? = null,
    onClickInfo: (() -> Unit)? = null,
    state: AnchoredDraggableState<DragValue>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset {
                IntOffset(
                    state
                        .requireOffset()
                        .roundToInt(), 0
                )
            }
            .anchoredDraggable(state, Orientation.Horizontal)
            .background(Color.White, RoundedCornerShape(Dimens.SpacingMedium))
            .combinedClickable(
                onClick = onClickCheckbox,
                onLongClick = onLongClickTask
            )
            .padding(Dimens.SpacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = completable.isDone,
            onCheckedChange = { onClickCheckbox() },
        )
        NkTextWithContentAfter(
            text = completable.name,
            style = NkTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        ) {
            if (tag != null) {
                Row {
                    Spacer(modifier = Modifier.width(Dimens.SpacingExtraSmall))
                    NkTag(text = tag)
                }
            }
        }
        if (onClickInfo != null) {
            NkInfoButton(
                onClick = onClickInfo,
                size = Dimens.IconSmall,
                modifier = Modifier.padding(Dimens.SpacingSmall)
            )
        }
    }
}

@Composable
fun TaskCardActionButton(
    action: TaskCardAction?,
    dragToCenter: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    if (action != null) {
        Box(
            modifier = Modifier
                .width(dragThresholdsDp.dp - Dimens.SpacingSmall)
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

enum class DragValue { Start, Center, End }

data class TaskCardAction(
    val backgroundColor: Color,
    val iconImageVector: ImageVector,
    val iconTint: Color,
    val onClick: () -> Unit
) {
    companion object {
        fun deleteAction(onClick: () -> Unit) = TaskCardAction(
            backgroundColor = Color.Red,
            iconImageVector = Icons.Default.Delete,
            iconTint = Color.White,
            onClick = onClick
        )
    }
}

@Composable
@Preview
fun TaskCardPreview() {
    TaskCard(
        completable = TutorialTask(0, 0, 0, "상점 재료 모으기: 목재 30개, 부드러운 목재 30개, 단단한 목재 30개, 철광석 30개", false, null),
        onClickCheckbox = {}
    )
}