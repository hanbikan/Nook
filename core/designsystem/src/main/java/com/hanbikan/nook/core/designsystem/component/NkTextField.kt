package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.theme.Fonts
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NkTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.height(48.dp),
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = NkTheme.colorScheme.secondaryContainer,
            cursorColor = NkTheme.colorScheme.secondary,
            focusedIndicatorColor = NkTheme.colorScheme.secondary,
            unfocusedIndicatorColor = NkTheme.colorScheme.secondary,
        ),
        textStyle = TextStyle(
            color = NkTheme.colorScheme.primary,
            fontFamily = Fonts.joa
        ),
    )
}

@Composable
fun NkPlaceholder(text: String) {
    NkText(
        text = text,
        color = NkTheme.colorScheme.primaryContainer,
    )
}

@Composable
@Preview
fun NkTextFieldPreview() {
    NkTextField("test", {})
}