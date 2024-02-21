package com.hanbikan.nook.feature.todo.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nook.core.designsystem.component.NkCheckboxWithTextSmall
import com.hanbikan.nook.core.designsystem.component.NkDialogWithTextField
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.feature.todo.R

@Composable
fun AddOrUpdateTaskDialog(
    status: AddOrUpdateTaskDialogStatus,
    dismissDialog: () -> Unit,
    addTask: (name: String, isDaily: Boolean, isVisible: Boolean) -> Unit,
    updateTask: (name: String, isDaily: Boolean, isVisible: Boolean) -> Unit,
) {
    val title = when (status) {
        is AddOrUpdateTaskDialogStatus.Add -> stringResource(id = R.string.add_task)
        is AddOrUpdateTaskDialogStatus.Update -> stringResource(id = R.string.update_task)
        is AddOrUpdateTaskDialogStatus.Invisible -> ""
    }
    var defaultInput = ""
    var defaultIsDaily = false
    var defaultIsVisible = true
    if (status is AddOrUpdateTaskDialogStatus.Update) {
        defaultInput = status.taskToUpdate.name
        defaultIsDaily = status.taskToUpdate.isDaily
        defaultIsVisible = status.taskToUpdate.isVisible
    }

    var isDaily by remember { mutableStateOf(defaultIsDaily) }
    var isVisible by remember { mutableStateOf(defaultIsVisible) }

    NkDialogWithTextField(
        visible = status !is AddOrUpdateTaskDialogStatus.Invisible,
        title = title,
        defaultInput = defaultInput,
        placeholder = stringResource(id = R.string.add_task_placeholder),
        onDismissRequest = dismissDialog,
        onConfirmation = { input ->
            when (status) {
                is AddOrUpdateTaskDialogStatus.Add -> addTask(input, isDaily, isVisible)
                is AddOrUpdateTaskDialogStatus.Update -> updateTask(input, isDaily, isVisible)
                is AddOrUpdateTaskDialogStatus.Invisible -> {}
            }
        },
    ) {
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            NkCheckboxWithTextSmall(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.repeat_daily),
                checked = isDaily,
                onCheckedChange = { isDaily = !isDaily },
            )
            NkCheckboxWithTextSmall(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.hiding),
                checked = !isVisible,
                onCheckedChange = { isVisible = !isVisible },
            )
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
    AddOrUpdateTaskDialog(status = AddOrUpdateTaskDialogStatus.Add, {}, { _, _, _-> }, { _, _, _ -> })
}