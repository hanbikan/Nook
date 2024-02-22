package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun TitleTextWithSpacer(
    title: String,
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    Column {
        Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingExtraSmall)
        ) {
            NkText(
                modifier = Modifier.padding(top = 2.dp), // NkText 글씨체가 아래에 공간을 살짝 두고 있기 때문에 정렬을 위해 패딩을 추가합니다.
                text = title,
                color = NkTheme.colorScheme.primaryContainer
            )
            content?.invoke(this)
        }
        Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
    }
}

@Composable
@Preview
fun TitleTextWithSpacerPreview() {
    TitleTextWithSpacer("진행도") {
        NkSmallButton(
            onClick = {},
            imageVector = Icons.Default.Info,
        )
    }
}