package com.hanbikan.nook.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
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
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.hanbikan.nook.core.designsystem.component.DragActions
import com.hanbikan.nook.core.designsystem.component.DragValue
import com.hanbikan.nook.core.designsystem.component.NkDragToAction
import com.hanbikan.nook.core.designsystem.component.NkInfoButton
import com.hanbikan.nook.core.designsystem.component.NkTag
import com.hanbikan.nook.core.designsystem.component.NkTextWithContentAfter
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Completable
import com.hanbikan.nook.core.domain.model.TutorialTask
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    completable: Completable,
    tag: String? = null,
    onClickCheckbox: () -> Unit,
    onLongClickTask: (() -> Unit)? = null,
    onClickInfo: (() -> Unit)? = null,
    dragActions: DragActions? = null,
) {
    Column {
        Box {
            if (dragActions != null) {
                NkDragToAction(dragActions = dragActions) {
                    TaskCardContent(
                        modifier = it,
                        completable = completable,
                        tag = tag,
                        onClickCheckbox = onClickCheckbox,
                        onLongClickTask = onLongClickTask,
                        onClickInfo = onClickInfo,
                    )
                }
            } else {
                TaskCardContent(
                    completable = completable,
                    tag = tag,
                    onClickCheckbox = onClickCheckbox,
                    onLongClickTask = onLongClickTask,
                    onClickInfo = onClickInfo,
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCardContent(
    modifier: Modifier = Modifier,
    completable: Completable,
    tag: String? = null,
    onClickCheckbox: () -> Unit,
    onLongClickTask: (() -> Unit)? = null,
    onClickInfo: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
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
@Preview
fun TaskCardPreview() {
    TaskCard(
        completable = TutorialTask(0, 0, 0, "상점 재료 모으기: 목재 30개, 부드러운 목재 30개, 단단한 목재 30개, 철광석 30개", false, null),
        onClickCheckbox = {}
    )
}