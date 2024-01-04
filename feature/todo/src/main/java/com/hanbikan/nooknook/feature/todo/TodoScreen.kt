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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nooknook.core.designsystem.component.AppBarIcon
import com.hanbikan.nooknook.core.designsystem.component.NnPrimaryText
import com.hanbikan.nooknook.core.designsystem.component.NnSecondaryText
import com.hanbikan.nooknook.core.designsystem.component.NnTopAppBar
import com.hanbikan.nooknook.core.designsystem.component.WithTitle
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme
import com.hanbikan.nooknook.core.domain.model.Task

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val userName = viewModel.userName.collectAsStateWithLifecycle().value
    val taskList = viewModel.taskList.collectAsStateWithLifecycle().value
    val doneTaskCount = viewModel.doneTaskCount.collectAsStateWithLifecycle().value

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
        TodoScreenContents(
            userName = userName,
            taskList = taskList,
            doneTaskCount = doneTaskCount,
            onClickCheckbox = viewModel::switchTask
        )
    }
}

@Composable
fun TodoScreenContents(
    userName: String,
    taskList: List<Task>,
    doneTaskCount: Int,
    onClickCheckbox: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(Dimens.SideMargin),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
    ) {
        NnPrimaryText(
            text = stringResource(id = R.string.welcome_text, userName),
            style = NnTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        WithTitle(title = stringResource(id = R.string.progress)) {
            ProgressCard(
                taskCount = taskList.size,
                doneTaskCount = doneTaskCount,
            )
        }
        WithTitle(title = stringResource(id = R.string.todo)) {
            TodoList(
                taskList = taskList,
                onClickCheckbox = onClickCheckbox,
            )
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
        // TODO: Dynamic color & animation
        LinearProgressIndicator(
            progress = progress,
            color = NnTheme.colorScheme.tertiary,
        )
    }
}

@Composable
fun TodoList(
    taskList: List<Task>,
    onClickCheckbox: (Int) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall),
    ) {
        itemsIndexed(taskList) { index, item ->
            TaskCard(
                task = item,
                onClickCheckbox = { onClickCheckbox(index) }
            )
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onClickCheckbox: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(Dimens.SpacingMedium))
            .clickable { onClickCheckbox() }
            .padding(Dimens.SpacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { onClickCheckbox() },
        )
        NnPrimaryText(
            text = task.name,
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