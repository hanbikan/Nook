package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NkCheckboxWithText(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable { onCheckedChange?.invoke(!checked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
        NkText(
            text = text,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun NkCheckboxWithTextSmall(
    text: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 12.sp), // Smaller font size
    checkboxSize: Dp = 16.dp, // Adjust if needed for visual consistency
) {
    Row(
        modifier = modifier.clickable { onCheckedChange?.invoke(!checked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Applying scale to Checkbox for visual size adjustment
        Box(
            modifier = Modifier.size(checkboxSize).graphicsLayer {
                // Calculate scale factor based on desired size and default size (20.dp assumed)
                val scale = checkboxSize.value / 20f
                scaleX = scale
                scaleY = scale
            },
            contentAlignment = Alignment.Center
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
        NkText(
            text = text,
            modifier = Modifier
                .weight(1f)
                .padding(start = checkboxSize / 4),
            style = textStyle
        )
    }
}


@Composable
@Preview
fun NkCheckboxWithTextPreview() {
    NkCheckboxWithText(text = "checkbox", checked = true, onCheckedChange = {})
}

@Composable
@Preview
fun NkCheckboxWithTextSmallPreview() {
    NkCheckboxWithTextSmall(text = "checkbox", checked = true, onCheckedChange = {})
}