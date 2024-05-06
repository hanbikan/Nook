package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar

val CollectibleItemSize = 100.dp

@Composable
fun MonthlyCollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: MuseumViewModel = hiltViewModel(),
) {
    val collectibles = viewModel.fishList.collectAsStateWithLifecycle().value

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.backAppBarIcon(onClick = navigateUp)
                ),
            )

            CollectibleList(collectibles = collectibles)
        }
    }
}