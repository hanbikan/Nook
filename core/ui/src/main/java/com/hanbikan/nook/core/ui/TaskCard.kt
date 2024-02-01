package com.hanbikan.nook.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Completable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    completable: Completable,
    onClickCheckbox: () -> Unit,
    onLongClickTask: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(Dimens.SpacingMedium))
                .combinedClickable(
                    onClick = onClickCheckbox,
                    onLongClick = onLongClickTask
                )
                .padding(Dimens.SpacingSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = completable.isDone,
                onCheckedChange = { onClickCheckbox() },
            )
            NkText(
                text = completable.name,
                style = NkTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
    }
}