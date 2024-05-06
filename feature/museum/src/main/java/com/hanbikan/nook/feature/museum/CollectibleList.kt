package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.domain.model.Collectible

@Composable
fun CollectibleList(
    collectibles: List<Collectible>
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
        items(collectibles) {
            CollectibleItem(it)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CollectibleItem(
    item: Collectible
) {
    Column(
        modifier = Modifier.size(CollectibleItemSize),
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