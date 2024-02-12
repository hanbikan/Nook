package com.hanbikan.nook.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.hanbikan.nook.core.designsystem.component.NkInfoButton
import com.hanbikan.nook.core.designsystem.component.NkTag
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTextWithContentAfter
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Completable
import com.hanbikan.nook.core.domain.model.TutorialTask

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    completable: Completable,
    tag: String? = null,
    onClickCheckbox: () -> Unit,
    onLongClickTask: (() -> Unit)? = null,
    onClickInfo: (() -> Unit)? = null,
) {
    Column {
        Row(
            modifier = Modifier
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
                        Spacer(modifier = Modifier.width(Dimens.SpacingSmall))
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
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
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