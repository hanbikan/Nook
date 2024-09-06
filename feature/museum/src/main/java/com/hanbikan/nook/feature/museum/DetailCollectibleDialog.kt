package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hanbikan.nook.core.designsystem.component.NkDialogWithContents
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTextButton
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.common.HasShadowMovement
import com.hanbikan.nook.core.domain.model.common.HasShadowSize
import com.hanbikan.nook.core.domain.model.common.LocationBased
import com.hanbikan.nook.core.domain.model.common.Monthly
import com.hanbikan.nook.core.domain.model.common.convertToTimeRanges
import com.hanbikan.nook.feature.museum.util.display

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailCollectibleDialog(
    collectible: Collectible?,
    onDismiss: () -> Unit,
    isNorth: Boolean,
) {
    NkDialogWithContents(
        visible = collectible != null,
        onDismissRequest = onDismiss
    ) {
        collectible?.let { item ->
            GlideImage(
                modifier = Modifier.size(CollectibleItemHeight * 0.75f),
                model = item.renderUrl,
                contentDescription = item.name,
            )

            Column {
                Spacer(modifier = Modifier.height(4.dp))
                NkText(text = stringResource(id = R.string.collectible_name, collectible.name))
                NkText(text = stringResource(id = R.string.bells_in_dialog, item.sellNook))
                if (collectible is Monthly) {
                    NkText(text = stringResource(id = R.string.collectible_time))
                    Column {
                        collectible.getCurrentMonthToTimes(isNorth)
                            .convertToTimeRanges()
                            .map { it.display() }
                            .forEach { display ->
                                NkText(text = display, style = NkTheme.typography.bodySmall)
                            }
                    }
                }
                if (collectible is LocationBased) {
                    NkText(text = stringResource(id = R.string.collectible_location, collectible.location))
                }
                if (collectible is HasShadowSize) {
                    NkText(text = stringResource(id = R.string.collectible_shadow_size, collectible.shadowSize))
                }
                if (collectible is HasShadowMovement) {
                    NkText(text = stringResource(id = R.string.collectible_shadow_size, collectible.shadowMovement))
                }
            }

            NkTextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onDismiss,
                text = stringResource(id = com.hanbikan.nook.core.designsystem.R.string.confirm),
            )
        }
    }
}