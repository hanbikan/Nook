package com.hanbikan.nooknook.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hanbikan.nooknook.core.designsystem.R
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NnDialogWithTextField(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    val input = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .padding(Dimens.SpacingExtraLarge),
            colors = CardDefaults.cardColors(containerColor = NnTheme.colorScheme.background),
            shape = RoundedCornerShape(Dimens.SpacingMedium),
        ) {
            Column(
                modifier = Modifier
                    .padding(Dimens.SpacingLarge, Dimens.SpacingMedium, Dimens.SpacingLarge, 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NnText(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = NnTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                NnTextField(
                    value = input.value,
                    onValueChange = {
                        input.value = it
                    },
                    singleLine = true,
                    modifier = Modifier.focusRequester(focusRequester)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    NnTextButton(
                        onClick = onDismissRequest,
                        text = stringResource(id = R.string.dismiss),
                        modifier = Modifier.weight(1f)
                    )
                    NnTextButton(
                        onClick = { onConfirmation(input.value) },
                        text = stringResource(id = R.string.confirm),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun NnDialogWithTextFieldPreview() {
    NnDialogWithTextField(title = "할 일 추가", onDismissRequest = {}, onConfirmation = {})
}