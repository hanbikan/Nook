package com.hanbikan.nooknook.core.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.hanbikan.nooknook.core.designsystem.theme.Fonts
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NnTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        singleLine = singleLine,
        maxLines = maxLines,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = NnTheme.colorScheme.secondaryContainer,
            cursorColor = NnTheme.colorScheme.secondary,
            focusedIndicatorColor = NnTheme.colorScheme.secondary,
            unfocusedIndicatorColor = NnTheme.colorScheme.secondary,
        ),
        textStyle = TextStyle(
            color = NnTheme.colorScheme.primary,
            fontFamily = Fonts.joa
        )
    )
}