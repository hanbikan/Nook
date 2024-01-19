package com.hanbikan.nookie.feature.todo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nookie.core.designsystem.component.AnimatedLinearProgressIndicator
import com.hanbikan.nookie.core.designsystem.component.AppBarIcon
import com.hanbikan.nookie.core.designsystem.component.FadeAnimatedVisibility
import com.hanbikan.nookie.core.designsystem.component.NkDialog
import com.hanbikan.nookie.core.designsystem.component.NkDialogWithTextField
import com.hanbikan.nookie.core.designsystem.component.NkText
import com.hanbikan.nookie.core.designsystem.component.NkTopAppBar
import com.hanbikan.nookie.core.designsystem.component.TitleTextWithSpacer
import com.hanbikan.nookie.core.designsystem.theme.Dimens
import com.hanbikan.nookie.core.designsystem.theme.NkTheme
import com.hanbikan.nookie.core.domain.model.Task
import com.hanbikan.nookie.core.ui.UserDialog

@Composable
fun TodoScreen(
    navigateToAddUser: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val isAddTaskDialogShown = viewModel.isAddTaskDialogShown.collectAsStateWithLifecycle().value
    val isDeleteTaskDialogShown = viewModel.isDeleteTaskDialogShown.collectAsStateWithLifecycle().value
    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val activeUser = viewModel.activeUser.collectAsStateWithLifecycle().value
    val taskList = viewModel.taskList.collectAsStateWithLifecycle().value
    val doneTaskCount = viewModel.doneTaskCount.collectAsStateWithLifecycle().value

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(NkTheme.colorScheme.background),
        ) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon(imageVector = Icons.Default.Home, onClick = { /* TODO: navigate to phone */ })
                ),
                rightAppBarIcons = listOf(
                    AppBarIcon(imageVector = Icons.Default.Person, onClick = viewModel::switchUserDialog),
                ),
            )
            Box {
                FadeAnimatedVisibility(visible = uiState is TodoUiState.Success.NotEmpty) {
                    TodoScreenSuccess(
                        userName = activeUser?.name ?: "",
                        taskList = taskList,
                        doneTaskCount = doneTaskCount,
                        onClickCheckbox = viewModel::switchTask,
                        onLongClickTask = viewModel::onLongClickTask
                    )
                }
                FadeAnimatedVisibility(visible = uiState is TodoUiState.Success.Empty) {
                    TodoScreenEmpty()
                }
                FadeAnimatedVisibility(visible = uiState is TodoUiState.Loading) {}
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Dimens.SpacingLarge),
            onClick = viewModel::switchAddTaskDialog,
            containerColor = NkTheme.colorScheme.primary,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_task),
            )
        }

        if (isAddTaskDialogShown) {
            NkDialogWithTextField(
                title = stringResource(id = R.string.add_task),
                placeholder = stringResource(id = R.string.add_task_placeholder),
                onDismissRequest = viewModel::switchAddTaskDialog,
                onConfirmation = { viewModel.addTask(it) }
            )
        }

        if (isDeleteTaskDialogShown) {
            NkDialog(
                description = stringResource(id = R.string.sure_to_delete_task),
                onDismissRequest = viewModel::switchDeleteTaskDialog,
                onConfirmation = viewModel::onConfirmDeleteTask
            )
        }

        if (isUserDialogShown) {
            UserDialog(
                navigateToAddUser = navigateToAddUser,
                onDismissRequest = viewModel::switchUserDialog
            )
        }
    }
}

@Composable
fun TodoScreenSuccess(
    userName: String,
    taskList: List<Task>,
    doneTaskCount: Int,
    onClickCheckbox: (Int) -> Unit,
    onLongClickTask: (Task) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(Dimens.SideMargin),
    ) {
        item {
            // Welcome message
            NkText(
                text = stringResource(id = R.string.welcome_text, userName),
                style = NkTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )

            // Progress card
            Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
            TitleTextWithSpacer(title = stringResource(id = R.string.progress))
            ProgressCard(
                taskCount = taskList.size,
                doneTaskCount = doneTaskCount,
            )

            // To-do list
            Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
            TitleTextWithSpacer(title = stringResource(id = R.string.todo))
        }
        itemsIndexed(taskList) { index, item ->
            TaskCard(
                task = item,
                onClickCheckbox = { onClickCheckbox(index) },
                onLongClickTask = { onLongClickTask(item) }
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
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
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    task: Task,
    onClickCheckbox: () -> Unit,
    onLongClickTask: () -> Unit,
) {
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
            checked = task.isDone,
            onCheckedChange = { onClickCheckbox() },
        )
        NkText(
            text = task.name,
            style = NkTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun TodoScreenEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.no_data),
            contentDescription = stringResource(id = R.string.empty_todo_list),
            modifier = Modifier.size(Dimens.IconMedium)
        )
        Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
        NkText(text = stringResource(id = R.string.empty_todo_list))
        Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
    }
}

@Preview
@Composable
fun TodoScreenPreview() {
    TodoScreen({})
}