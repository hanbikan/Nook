package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.hanbikan.nook.core.designsystem.R
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun NkDialog(
    visible: Boolean,
    description: String,
    imageUrl: String? = null,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    confirmText: String = NkDialogDefaults.confirmText,
    hasOnlyConfirmationButton: Boolean = NkDialogDefaults.hasOnlyConfirmationButton,
) {
    // 텍스트가 너무 많으면 글씨 크기를 줄입니다.
    val textStyle = if (description.length < NkDialogDefaults.descriptionLengthThreshold) {
        NkTheme.typography.titleMedium
    } else {
        NkTheme.typography.titleSmall
    }

    NkDialogWithContents(visible, onDismissRequest) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1.0f)
                    .padding(0.dp, 0.dp, 0.dp, Dimens.SpacingSmall),
                contentScale = ContentScale.Crop,
            )
        }
        NkText(
            text = description,
            style = textStyle,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (!hasOnlyConfirmationButton) {
                NkTextButton(
                    onClick = onDismissRequest,
                    text = NkDialogDefaults.dismissText,
                    modifier = Modifier.weight(1f)
                )
            }
            NkTextButton(
                onClick = onConfirmation,
                text = confirmText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun NkDialogWithContents(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    NkDialogBase(visible, onDismissRequest) {
        Column(
            modifier = Modifier
                .padding(Dimens.SpacingLarge, Dimens.SpacingMedium, Dimens.SpacingLarge, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
        }
    }
}

@Composable
fun NkDialogBase(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    if (!visible) return

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

object NkDialogDefaults {
    val confirmText: String @Composable get() = stringResource(id = R.string.confirm)

    val dismissText: String @Composable get() = stringResource(id = R.string.dismiss)

    const val hasOnlyConfirmationButton: Boolean = false

    const val descriptionLengthThreshold: Int = 15
}

@Composable
@Preview
fun NkDialogPreview() {
    NkDialog(
        visible = true,
        description = "할 일을 삭제합니다. 이 작업은 되돌릴 수 없습니다.",
        onDismissRequest = {},
        onConfirmation = {},
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/acnh-1be21.appspot.com/o/eight_rocks.png?alt=media&token=5fa0f8d2-159c-4d86-8a83-8c7ef43c46b6"
    )
}