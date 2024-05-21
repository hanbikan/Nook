package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.domain.model.Collectible

private val CollectibleItemSize = 80.dp

@Composable
fun CollectibleList(
    collectibles: List<Collectible>,
    onClickCollectibleItem: (Int) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(CollectibleItemSize)) {
        itemsIndexed(collectibles) { index, item ->
            CollectibleItem(
                item = item,
                onClick = { onClickCollectibleItem(index) }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CollectibleItem(
    item: Collectible,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .size(CollectibleItemSize)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GlideImage(
            modifier = Modifier.size(CollectibleItemSize * 0.5f),
            model = item.imageUrl,
            contentDescription = item.name,
        )
        NkText(text = item.name)
    }
}