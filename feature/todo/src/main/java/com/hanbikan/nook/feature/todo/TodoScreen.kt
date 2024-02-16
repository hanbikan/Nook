package com.hanbikan.nook.feature.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.FadeAnimatedVisibility
import com.hanbikan.nook.core.designsystem.component.NkDialog
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.component.TitleTextWithSpacer
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.core.ui.ProgressCard
import com.hanbikan.nook.core.ui.TaskCard
import com.hanbikan.nook.core.designsystem.component.DragAction
import com.hanbikan.nook.core.designsystem.component.DragActions
import com.hanbikan.nook.core.designsystem.getAlphaByEnabled
import com.hanbikan.nook.core.ui.UserDialog
import com.hanbikan.nook.core.ui.WelcomeText
import com.hanbikan.nook.feature.todo.component.AddOrUpdateTaskDialog
import com.hanbikan.nook.feature.todo.component.AddOrUpdateTaskDialogStatus

@Composable
fun TodoScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val addOrUpdateTaskDialogStatus = viewModel.addOrUpdateTaskDialogStatus.collectAsStateWithLifecycle().value
    val isDeleteTaskDialogShown = viewModel.isDeleteTaskDialogShown.collectAsStateWithLifecycle().value
    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val activeUser = viewModel.activeUser.collectAsStateWithLifecycle().value
    val visibleTaskList = viewModel.visibleTaskList.collectAsStateWithLifecycle().value
    val invisibleTaskList = viewModel.invisibleTaskList.collectAsStateWithLifecycle().value
    val showsAllItems = viewModel.showsAllItems.collectAsStateWithLifecycle().value

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.appListAppBarIcon(onClick = navigateToPhone)
                ),
                rightAppBarIcons = listOf(
                    AppBarIcon.userDialogAppBarIcon(onClick = viewModel::switchUserDialog)
                ),
            )
            Box {
                FadeAnimatedVisibility(visible = uiState is TodoUiState.Success.NotEmpty) {
                    TodoScreenSuccess(
                        userName = activeUser?.name ?: "",
                        visibleTaskList = visibleTaskList,
                        invisibleTaskList = invisibleTaskList,
                        onClickCheckbox = viewModel::switchTask,
                        onLongClickTask = viewModel::onLongClickTask,
                        onClickDeleteAction = viewModel::onClickDeleteAction,
                        showsAllItems = showsAllItems,
                        switchShowsAllItems = viewModel::switchShowsAllItems,
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
            onClick = { viewModel.setAddOrUpdateTaskDialogStatus(AddOrUpdateTaskDialogStatus.Add) },
            containerColor = NkTheme.colorScheme.primary,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_task),
                tint = Color.White,
            )
        }

        AddOrUpdateTaskDialog(
            status = addOrUpdateTaskDialogStatus,
            dismissDialog = { viewModel.setAddOrUpdateTaskDialogStatus(AddOrUpdateTaskDialogStatus.Invisible) },
            addTask = viewModel::addTask,
            updateTask = viewModel::updateTask,
        )

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
    visibleTaskList: List<Task>,
    invisibleTaskList: List<Task>,
    onClickCheckbox: (Task) -> Unit,
    onLongClickTask: (Task) -> Unit,
    onClickDeleteAction: (Task) -> Unit,
    showsAllItems: Boolean,
    switchShowsAllItems: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(Dimens.SideMargin),
    ) {
        item {
            WelcomeText(userName = userName)

            // Progress card
            TitleTextWithSpacer(title = stringResource(id = R.string.progress))
            ProgressCard(completableList = visibleTaskList)

            // To-do list
            TitleTextWithSpacer(title = stringResource(id = R.string.todo)) {
                VisibilityButton(
                    showsAllItems = showsAllItems,
                    switchShowsAllItems = switchShowsAllItems,
                )
            }
        }
        items(visibleTaskList) { item ->
            TaskCard(
                completable = item,
                onClickCheckbox = { onClickCheckbox(item) },
                onLongClickTask = { onLongClickTask(item) },
                tag = if (item.isDaily) stringResource(id = R.string.daily) else null,
                dragActions = DragActions.withSameActions(
                    action = DragAction.deleteAction { onClickDeleteAction(item) }
                ),
            )
        }
        if (showsAllItems) {
            items(invisibleTaskList) { item ->
                TaskCard(
                    completable = item,
                    onClickCheckbox = { onClickCheckbox(item) },
                    onLongClickTask = { onLongClickTask(item) },
                    tag = if (item.isDaily) stringResource(id = R.string.daily) else null,
                    dragActions = DragActions.withSameActions(
                        action = DragAction.deleteAction { onClickDeleteAction(item) }
                    ),
                    enabled = false
                )
            }
        }
    }
}

@Composable
fun VisibilityButton(
    showsAllItems: Boolean,
    switchShowsAllItems: () -> Unit,
) {
    val size = Dimens.IconExtraSmall
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    Icon(
        imageVector = ImageVector.vectorResource(
            id = if (showsAllItems) R.drawable.visibility else R.drawable.visibility_off
        ),
        contentDescription = stringResource(
            id = if (showsAllItems) R.string.show_all_items else R.string.show_only_visible_items
        ),
        modifier = Modifier
            .size(size)
            .clickable(
                onClick = switchShowsAllItems,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = size
                )
            )
            .alpha(alpha = getAlphaByEnabled(showsAllItems))
    )
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
    TodoScreen({}, {})
}