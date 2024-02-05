package com.hanbikan.nook.feature.tutorial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.component.TitleTextWithSpacer
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.ui.ProgressCard
import com.hanbikan.nook.core.ui.TaskCard
import com.hanbikan.nook.core.ui.UserDialog
import com.hanbikan.nook.core.ui.WelcomeText

@Composable
fun TutorialScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    viewModel: TutorialViewModel = hiltViewModel(),
) {
    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val tutorialDayRange = viewModel.tutorialDayRange.collectAsStateWithLifecycle().value
    val activeUser = viewModel.activeUser.collectAsStateWithLifecycle().value
    val tutorialTaskList = viewModel.tutorialTaskList.collectAsStateWithLifecycle().value

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
            FadeAnimatedVisibility(visible = uiState is TutorialUiState.Success) {
                Column {
                    LazyColumn(
                        modifier = Modifier
                            .padding(Dimens.SideMargin)
                            .weight(1f),
                    ) {
                        item {
                            WelcomeText(userName = activeUser?.name ?: "")

                            // Progress card
                            TitleTextWithSpacer(title = stringResource(id = R.string.progress_by_day, activeUser?.tutorialDay ?: 0))
                            ProgressCard(completableList = tutorialTaskList)

                            // Today's tutorial task list
                            TitleTextWithSpacer(title = stringResource(id = R.string.today_task))
                        }
                        itemsIndexed(tutorialTaskList) { index, item ->
                            TaskCard(
                                completable = item,
                                onClickCheckbox = { viewModel.switchTutorialTask(index) },
                                onLongClickTask = {}
                            )
                        }
                    }
                    Row {
                        IconButton(
                            onClick = viewModel::decreaseTutorialDay,
                            modifier = Modifier.weight(1f),
                            enabled = activeUser != null && tutorialDayRange != null && (activeUser.tutorialDay - 1) in tutorialDayRange
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = stringResource(id = R.string.previous),
                            )
                        }
                        IconButton(
                            onClick = viewModel::increaseTutorialDay,
                            modifier = Modifier.weight(1f),
                            enabled = activeUser != null && tutorialDayRange != null && (activeUser.tutorialDay + 1) in tutorialDayRange
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = stringResource(id = R.string.next),
                            )
                        }
                    }
                }
            }
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
@Preview
fun TutorialScreenPreview() {
    TutorialScreen({}, {})
}