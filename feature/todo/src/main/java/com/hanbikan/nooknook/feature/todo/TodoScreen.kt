package com.hanbikan.nooknook.feature.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nooknook.core.designsystem.component.AppBarIcon
import com.hanbikan.nooknook.core.designsystem.component.NnPrimaryText
import com.hanbikan.nooknook.core.designsystem.component.NnSecondaryText
import com.hanbikan.nooknook.core.designsystem.component.NnTopAppBar
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@Composable
fun TodoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NnTheme.colorScheme.background),
    ) {
        NnTopAppBar(
            leftAppBarIcons = listOf(
                AppBarIcon(imageVector = Icons.Default.Home, onClick = {})
            ),
            rightAppBarIcons = listOf(
                AppBarIcon(imageVector = Icons.Default.Person, onClick = {}),
            ),
        )
        TodoContents()
    }
}

@Composable
fun TodoContents() {
    Column(
        modifier = Modifier
            .padding(Dimens.SideMargin),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
    ) {
        NnPrimaryText(
            text = stringResource(id = R.string.welcome_text, "Isabelle"),
            style = NnTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Progress()
    }
}

@Composable
fun Progress() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
    ) {
        NnSecondaryText(
            text = stringResource(id = R.string.progress),
        )
        ProgressCard(10, 7)
    }
}

@Composable
fun ProgressCard(
    taskCount: Int,
    doneTaskCount: Int,
) {
    val progress: Float = doneTaskCount.toFloat() / taskCount
    val doneTaskPercent: Int = (progress * 100).toInt()

    Column(
        modifier = Modifier
            .width(220.dp)
            .background(Color.White, RoundedCornerShape(Dimens.SpacingMedium))
            .padding(Dimens.SpacingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall)
    ) {
        NnSecondaryText(
            text = stringResource(id = R.string.task_count, taskCount),
            style = NnTheme.typography.bodySmall
        )
        NnPrimaryText(
            text = stringResource(id = R.string.done_task_percent, doneTaskPercent),
            style = NnTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        LinearProgressIndicator(
            progress = progress,
            color = NnTheme.colorScheme.tertiary,
        )
    }
}

@Preview
@Composable
fun TodoScreenPreview() {
    TodoScreen()
}