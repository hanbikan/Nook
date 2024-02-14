package com.hanbikan.nook.feature.todo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.component.NkCheckboxWithText
import com.hanbikan.nook.core.designsystem.component.NkDialogBase
import com.hanbikan.nook.core.designsystem.component.NkPlaceholder
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTextButton
import com.hanbikan.nook.core.designsystem.component.NkTextField
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.feature.todo.R

@Composable
fun AddOrUpdateTaskDialog(
    status: AddOrUpdateTaskDialogStatus,
    dismissDialog: () -> Unit,
    addTask: (name: String, isDaily: Boolean) -> Unit,
    updateTask: (name: String, isDaily: Boolean) -> Unit,
) {
    if (status is AddOrUpdateTaskDialogStatus.Invisible) return

    val title = when (status) {
        is AddOrUpdateTaskDialogStatus.Add -> stringResource(id = R.string.add_task)
        is AddOrUpdateTaskDialogStatus.Update -> stringResource(id = R.string.update_task)
        is AddOrUpdateTaskDialogStatus.Invisible -> ""
    }

    val defaultInput = if (status is AddOrUpdateTaskDialogStatus.Update) {
        status.taskToUpdate.name
    } else {
        ""
    }

    val defaultIsDaily = if (status is AddOrUpdateTaskDialogStatus.Update) {
        status.taskToUpdate.isDaily
    } else {
        false
    }

    AddOrUpdateTaskDialog(
        title = title,
        defaultInput = defaultInput,
        defaultIsDaily = defaultIsDaily,
        status = status,
        dismissDialog = dismissDialog,
        addTask = addTask,
        updateTask = updateTask
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddOrUpdateTaskDialog(
    title: String,
    defaultInput: String,
    defaultIsDaily: Boolean,
    status: AddOrUpdateTaskDialogStatus,
    dismissDialog: () -> Unit,
    addTask: (name: String, isDaily: Boolean) -> Unit,
    updateTask: (name: String, isDaily: Boolean) -> Unit,
) {
    var input by remember { mutableStateOf(defaultInput) }
    var isDaily by remember { mutableStateOf(defaultIsDaily) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    NkDialogBase(dismissDialog) {
        Column(
            modifier = Modifier
                .padding(Dimens.SpacingLarge, Dimens.SpacingMedium, Dimens.SpacingLarge, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NkText(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = NkTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
            NkTextField(
                value = input,
                onValueChange = { input = it },
                singleLine = true,
                placeholder = { NkPlaceholder(text = stringResource(id = R.string.add_task_placeholder)) },
                modifier = Modifier.focusRequester(focusRequester),
            )

            Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
            Row(modifier = Modifier.fillMaxWidth()) {
                NkCheckboxWithText(
                    text = stringResource(id = R.string.repeat_daily),
                    checked = isDaily,
                    onCheckedChange = { isDaily = !isDaily }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                NkTextButton(
                    onClick = dismissDialog,
                    text = stringResource(id = com.hanbikan.nook.core.designsystem.R.string.dismiss),
                    modifier = Modifier.weight(1f)
                )
                NkTextButton(
                    onClick = {
                        when (status) {
                            is AddOrUpdateTaskDialogStatus.Add -> addTask(input, isDaily)
                            is AddOrUpdateTaskDialogStatus.Update -> updateTask(input, isDaily)
                            is AddOrUpdateTaskDialogStatus.Invisible -> {}
                        }
                    },
                    text = stringResource(id = com.hanbikan.nook.core.designsystem.R.string.confirm),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

sealed interface AddOrUpdateTaskDialogStatus {
    object Add: AddOrUpdateTaskDialogStatus
    data class Update(val taskToUpdate: Task): AddOrUpdateTaskDialogStatus
    object Invisible: AddOrUpdateTaskDialogStatus
}

@Composable
@Preview
fun AddOrUpdateTaskDialogPreview() {
    AddOrUpdateTaskDialog(status = AddOrUpdateTaskDialogStatus.Add, {}, { _, _ -> }, { _, _ -> })
}