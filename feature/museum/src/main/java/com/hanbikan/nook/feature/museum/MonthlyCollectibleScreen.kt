package com.hanbikan.nook.feature.museum

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

val CollectibleItemSize = 100.dp

@Composable
fun MonthlyCollectibleScreen(
    viewModel: MuseumViewModel = hiltViewModel(),
) {
    val collectibles = viewModel.fishList.collectAsStateWithLifecycle().value
    CollectibleList(collectibles = collectibles)
}