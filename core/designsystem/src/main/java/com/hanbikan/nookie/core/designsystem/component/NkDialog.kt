package com.hanbikan.nookie.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hanbikan.nookie.core.designsystem.R
import com.hanbikan.nookie.core.designsystem.theme.Dimens
import com.hanbikan.nookie.core.designsystem.theme.NkTheme

@Composable
fun NkDialog(
    description: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    NkDialogBase(onDismissRequest) {
        Column(
            modifier = Modifier
                .padding(Dimens.SpacingLarge, Dimens.SpacingMedium, Dimens.SpacingLarge, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NkText(
                text = description,
                style = NkTheme.typography.titleMedium
            )
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
                    onClick = onConfirmation,
                    text = stringResource(id = R.string.confirm),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun NkDialogBase(
    onDismissRequest: () -> Unit,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .padding(Dimens.SpacingExtraLarge),
            colors = CardDefaults.cardColors(containerColor = NkTheme.colorScheme.background),
            shape = RoundedCornerShape(Dimens.SpacingMedium),
            content = content,
        )
    }
}

@Composable
@Preview
fun NkDialogPreview() {
    NkDialog(description = "할 일을 삭제합니다.", onDismissRequest = {}, onConfirmation = {})
}