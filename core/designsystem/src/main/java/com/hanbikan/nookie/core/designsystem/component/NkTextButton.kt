package com.hanbikan.nookie.core.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nookie.core.designsystem.theme.Dimens
import com.hanbikan.nookie.core.designsystem.theme.NkTheme

@Composable
fun NkTextButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(Dimens.SpacingMedium),
        colors = ButtonDefaults.buttonColors(
            containerColor = NkTheme.colorScheme.tertiary,
        )
    ) {
        NkText(
            text = text,
            color = NkTheme.colorScheme.tertiaryContainer,
        )
    }
}

@Composable
@Preview
fun NkTextButtonPreview() {
    NkTextButton(onClick = {}, text = "Text")
}