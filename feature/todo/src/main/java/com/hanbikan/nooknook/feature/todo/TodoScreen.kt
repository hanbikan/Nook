package com.hanbikan.nooknook.feature.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hanbikan.nooknook.core.designsystem.component.AppBarIcon
import com.hanbikan.nooknook.core.designsystem.component.NnPrimaryText
import com.hanbikan.nooknook.core.designsystem.component.NnSecondaryText
import com.hanbikan.nooknook.core.designsystem.component.NnTopAppBar
import com.hanbikan.nooknook.core.designsystem.component.WithTitle
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel(),
) {
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
        TodoScreenContents()
    }
}

@Composable
fun TodoScreenContents() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(Dimens.SideMargin),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
    ) {
        NnPrimaryText(
            text = stringResource(id = R.string.welcome_text, "Isabelle"),
            style = NnTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        WithTitle(title = stringResource(id = R.string.progress)) {
            ProgressCard(10, 7)
        }
        WithTitle(title = stringResource(id = R.string.todo)) {
            TodoList()
        }
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
            .padding(Dimens.SpacingMedium, Dimens.SpacingLarge),
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

@Composable
fun TodoList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall),
    ) {
        TaskCard(false, "Daily meeting")
        TaskCard(false, "Daily meeting")
        TaskCard(false, "Daily meeting")
        TaskCard(false, "Daily meeting")
        TaskCard(false, "Daily meeting")
        TaskCard(false, "Daily meeting")
        TaskCard(false, "Daily meeting")
        TaskCard(false, "Daily meeting")
    }
}

@Composable
fun TaskCard(
    isDone: Boolean,
    name: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(Dimens.SpacingMedium))
            .clickable { /* TODO: Switch checkbox */ }
            .padding(Dimens.SpacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isDone,
            onCheckedChange = {},
        )
        NnPrimaryText(
            text = name,
            style = NnTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun TodoScreenPreview() {
    TodoScreen()
}