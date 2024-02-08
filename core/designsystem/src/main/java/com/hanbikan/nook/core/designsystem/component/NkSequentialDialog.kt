package com.hanbikan.nook.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.hanbikan.nook.core.designsystem.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NkSequentialDialog(
    description: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    hasOnlyConfirmationButton: Boolean = false,
) {
    var descriptions by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        // description을 개행문자로 자릅니다.
        launch(Dispatchers.Default) {
            descriptions = description.split(Regex("[\n]")).map { it.trim() }.filter { it.isNotEmpty() }
        }
    }
    NkSequentialDialog(
        descriptions = descriptions,
        onDismissRequest = onDismissRequest,
        onConfirmation = onConfirmation,
        hasOnlyConfirmationButton = hasOnlyConfirmationButton,
    )
}

@Composable
fun NkSequentialDialog(
    descriptions: List<String>,
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
        description = descriptions[index],
        onDismissRequest = onDismissRequest,
        onConfirmation = dynamicOnConfirmation,
        confirmText = dynamicConfirmText,
        hasOnlyConfirmationButton = dynamicHasOnlyConfirmationButton
    )
}