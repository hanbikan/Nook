package com.hanbikan.nooknook.core.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@Composable
fun NnTextButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(Dimens.SpacingMedium),
        colors = ButtonDefaults.buttonColors(
            containerColor = NnTheme.colorScheme.tertiary,
        )
    ) {
        NnText(
            text = text,
            color = NnTheme.colorScheme.tertiaryContainer,
        )
    }
}