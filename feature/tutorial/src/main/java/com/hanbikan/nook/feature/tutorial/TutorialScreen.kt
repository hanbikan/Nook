package com.hanbikan.nook.feature.tutorial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.FadeAnimatedVisibility
import com.hanbikan.nook.core.designsystem.component.NkDialog
import com.hanbikan.nook.core.designsystem.component.NkInfoButton
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.component.TitleTextWithSpacer
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.domain.model.Detail
import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.ui.DetailDialog
import com.hanbikan.nook.core.ui.ProgressCard
import com.hanbikan.nook.core.ui.TaskCard
import com.hanbikan.nook.core.ui.UserDialog
import com.hanbikan.nook.core.ui.WelcomeText

@Composable
fun TutorialScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    navigateToTodo: () -> Unit,
    viewModel: TutorialViewModel = hiltViewModel(),
) {
    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value
    val isProgressCardInfoDialogShown =
        viewModel.isProgressCardInfoDialogShown.collectAsStateWithLifecycle().value
    val isDetailDialogShown = viewModel.isDetailDialogShown.collectAsStateWithLifecycle().value
    val isNextDayDialogShown = viewModel.isNextDayDialogShown.collectAsStateWithLifecycle().value
    val isTutorialEndDialogShown = viewModel.isTutorialEndDialogShown.collectAsStateWithLifecycle().value

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val tutorialDayRange = viewModel.tutorialDayRange.collectAsStateWithLifecycle().value
    val activeUser = viewModel.activeUser.collectAsStateWithLifecycle().value ?: User.DEFAULT
    val tutorialTaskList = viewModel.tutorialTaskList.collectAsStateWithLifecycle().value
    val detailsToShow = viewModel.detailsToShow.collectAsStateWithLifecycle().value

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.appListAppBarIcon(onClick = navigateToPhone)
                ),
                rightAppBarIcons = listOf(
                    AppBarIcon.userDialogAppBarIcon(onClick = viewModel::switchUserDialog)
                ),
            )
            TutorialScreenContents(
                uiState = uiState,
                activeUser = activeUser,
                tutorialTaskList = tutorialTaskList,
                switchTutorialTask = viewModel::switchTutorialTask,
                decreaseTutorialDay = viewModel::decreaseTutorialDay,
                increaseTutorialDay = viewModel::increaseTutorialDay,
                tutorialDayRange = tutorialDayRange,
                switchProgressCardInfoDialog = viewModel::switchProgressCardInfoDialog,
                onClickInfo = viewModel::showDetailDialog,
            )
        }

        if (isUserDialogShown) {
            UserDialog(
                navigateToAddUser = navigateToAddUser,
                onDismissRequest = viewModel::switchUserDialog
            )
        }

        if (isProgressCardInfoDialogShown) {
            NkDialog(
                description = stringResource(id = R.string.progress_card_description),
                onDismissRequest = viewModel::switchProgressCardInfoDialog,
                onConfirmation = viewModel::switchProgressCardInfoDialog,
                hasOnlyConfirmationButton = true
            )
        }

        DetailDialog(
            detailsToShow = detailsToShow,
            isDetailDialogShown = isDetailDialogShown,
            hideDetailDialog = viewModel::hideDetailDialog
        )

        if (isNextDayDialogShown) {
            NkDialog(
                description = stringResource(
                    id = R.string.move_to_next_day_description,
                    activeUser.tutorialDay + 1
                ),
                onDismissRequest = viewModel::switchNextDayDialog,
                onConfirmation = {
                    viewModel.increaseTutorialDay()
                    viewModel.switchNextDayDialog()
                }
            )
        }

        if (isTutorialEndDialogShown) {
            NkDialog(
                description = stringResource(id = R.string.move_to_todo_description),
                onDismissRequest = viewModel::switchTutorialEndDialog,
                onConfirmation = navigateToTodo
            )
        }
    }
}

@Composable
fun TutorialScreenContents(
    uiState: TutorialUiState,
    activeUser: User,
    tutorialTaskList: List<TutorialTask>,
    switchTutorialTask: (Int) -> Unit,
    decreaseTutorialDay: () -> Unit,
    increaseTutorialDay: () -> Unit,
    tutorialDayRange: IntRange?,
    switchProgressCardInfoDialog: () -> Unit,
    onClickInfo: (List<Detail>) -> Unit,
) {
    FadeAnimatedVisibility(visible = uiState is TutorialUiState.Success) {
        Column {
            LazyColumn(
                modifier = Modifier
                    .padding(Dimens.SideMargin)
                    .weight(1f),
            ) {
                item {
                    WelcomeText(userName = activeUser.name)

                    // Progress card
                    TitleTextWithSpacer(
                        title = stringResource(
                            id = R.string.progress_by_day,
                            activeUser.tutorialDay
                        )
                    ) {
                        NkInfoButton(onClick = switchProgressCardInfoDialog)
                    }
                    ProgressCard(completableList = tutorialTaskList)

                    // Today's tutorial task list
                    TitleTextWithSpacer(title = stringResource(id = R.string.today_task))
                }
                itemsIndexed(tutorialTaskList) { index, item ->
                    TaskCard(
                        completable = item,
                        onClickCheckbox = { switchTutorialTask(index) },
                        onClickInfo = item.details?.let { { onClickInfo(it) } }
                    )
                }
            }
            Row {
                IconButton(
                    onClick = decreaseTutorialDay,
                    modifier = Modifier.weight(1f),
                    enabled = tutorialDayRange != null && (activeUser.tutorialDay - 1) in tutorialDayRange
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(id = R.string.previous),
                    )
                }
                IconButton(
                    onClick = increaseTutorialDay,
                    modifier = Modifier.weight(1f),
                    enabled = tutorialDayRange != null && (activeUser.tutorialDay + 1) in tutorialDayRange
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(id = R.string.next),
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun TutorialScreenPreview() {
    TutorialScreen({}, {}, {})
}