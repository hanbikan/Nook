package com.hanbikan.nook.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import com.hanbikan.nook.core.designsystem.R

@Composable
fun NkSequentialDialog(
    visible: Boolean,
    descriptions: List<String>,
    painters: List<Painter?>,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    hasOnlyConfirmationButton: Boolean = false,
) {
    if (descriptions.isEmpty()) return

    var index by remember { mutableStateOf(0) }

    val isLastIndex = index == descriptions.lastIndex
    val dynamicOnConfirmation = if (!isLastIndex) { { index += 1 } } else { onConfirmation }
    val dynamicConfirmText = if (!isLastIndex) { stringResource(id = R.string.next) } else { NkDialogDefaults.confirmText }
    val dynamicHasOnlyConfirmationButton = if (!isLastIndex) { NkDialogDefaults.hasOnlyConfirmationButton } else { hasOnlyConfirmationButton }

    NkDialog(
        visible = visible,
        description = descriptions[index],
        painter = painters[index],
        onDismissRequest = onDismissRequest,
        onConfirmation = dynamicOnConfirmation,
        confirmText = dynamicConfirmText,
        hasOnlyConfirmationButton = dynamicHasOnlyConfirmationButton
    )
}