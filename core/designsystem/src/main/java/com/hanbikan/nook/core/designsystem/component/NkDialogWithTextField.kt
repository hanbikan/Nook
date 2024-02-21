package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.R
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun NkDialogWithTextField(
    visible: Boolean,
    title: String,
    defaultInput: String = "",
    placeholder: String = "",
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    contentBelowTextField: @Composable ColumnScope.() -> Unit = {},
) {
    if (!visible) return

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var textFieldValue by remember { mutableStateOf(
        TextFieldValue(
            text = defaultInput,
            selection = TextRange(defaultInput.length)
        )
    ) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    NkDialogBase(true, onDismissRequest) {
        Column(
            modifier = Modifier
                .padding(Dimens.SpacingLarge, Dimens.SpacingMedium, Dimens.SpacingLarge, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 제목
            NkText(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = NkTheme.typography.titleMedium
            )

            // 입력
            Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
            NkTextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                singleLine = true,
                placeholder = { NkPlaceholder(text = placeholder) },
                modifier = Modifier.focusRequester(focusRequester),
            )

            contentBelowTextField()

            // 하단 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                NkTextButton(
                    onClick = onDismissRequest,
                    text = stringResource(id = R.string.dismiss),
                    modifier = Modifier.weight(1f)
                )
                NkTextButton(
                    onClick = { onConfirmation(textFieldValue.text) },
                    text = stringResource(id = R.string.confirm),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
@Preview
fun NkDialogWithTextFieldPreview() {
    NkDialogWithTextField(visible = true, title = "할 일 추가", onDismissRequest = {}, onConfirmation = {})
}