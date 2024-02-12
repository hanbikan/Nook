package com.hanbikan.nook.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.component.AnimatedLinearProgressIndicator
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Completable

@Composable
fun ProgressCard(
    completableList: List<Completable>
) {
    val taskCount = completableList.size
    val doneTaskCount = completableList.count { it.isDone }
    val progress: Float = doneTaskCount.toFloat() / taskCount
    val doneTaskPercent: Int = (progress * 100).toInt()

    Column(
        modifier = Modifier
            .width(220.dp)
            .background(Color.White, RoundedCornerShape(Dimens.SpacingMedium))
            .padding(Dimens.SpacingMedium, Dimens.SpacingLarge),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall)
    ) {
        NkText(
            text = stringResource(id = R.string.task_count, taskCount),
            style = NkTheme.typography.bodySmall,
            color = NkTheme.colorScheme.primaryContainer,
        )
        NkText(
            text = stringResource(id = R.string.done_task_percent, doneTaskPercent),
            style = NkTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        AnimatedLinearProgressIndicator(
            progress = progress,
            color = NkTheme.colorScheme.tertiary,
            trackColor = Color.White
        )
    }
}