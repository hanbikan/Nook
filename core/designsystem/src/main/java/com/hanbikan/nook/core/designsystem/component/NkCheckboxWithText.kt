package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NkCheckboxWithText(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable {
                if (onCheckedChange != null) {
                    onCheckedChange(!checked)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        NkText(
            text = text,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@Preview
fun NkCheckboxWithTextPreview() {
    NkCheckboxWithText(text = "checkbox", checked = true, onCheckedChange = {})
}