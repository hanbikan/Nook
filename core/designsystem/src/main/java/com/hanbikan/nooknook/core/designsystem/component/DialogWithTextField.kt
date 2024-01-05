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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@Composable
fun DialogWithTextField(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    val input = remember { mutableStateOf("") }

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
                    text = "할 일",
                    style = NnTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                NnTextField(
                    value = input.value,
                    onValueChange = {
                        input.value = it
                    },
                    singleLine = true,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    NnTextButton(
                        onClick = onDismissRequest,
                        text = "취소",
                        modifier = Modifier.weight(1f)
                    )
                    NnTextButton(
                        onClick = { onConfirmation(input.value) },
                        text = "확인",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun DialogWithTextFieldPreview() {
    DialogWithTextField({}, {})
}