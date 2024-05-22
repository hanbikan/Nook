package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.ChipGroup
import com.hanbikan.nook.core.designsystem.component.ChipItem
import com.hanbikan.nook.core.designsystem.component.NkAnimatedCircularProgress
import com.hanbikan.nook.core.designsystem.component.NkChipGroup
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Collectible
import com.hanbikan.nook.core.domain.model.calculateProgress

@Composable
fun CollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: CollectibleViewModel = hiltViewModel(),
    // TODO: isMonthly? -> false일 경우 chip group 제거
) {
    val collectibles = viewModel.collectibleList.collectAsStateWithLifecycle().value
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.backAppBarIcon(onClick = navigateUp)
                ),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SideMargin, 0.dp),
            ) {
                if (uiState.chipIndex != null) {
                    NkChipGroup(
                        chipGroup = ChipGroup(
                            chips = listOf(
                                ChipItem(stringResource(id = R.string.overall)),
                                ChipItem(stringResource(id = R.string.monthly))
                            ),
                            selectedIndex = uiState.chipIndex
                        ),
                        isLarge = true,
                        onClickItem = viewModel::onClickViewType,
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
                }

                when (uiState) {
                    is CollectibleScreenUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is CollectibleScreenUiState.OverallView -> {
                        OverallCollectibleContents(
                            collectibles = collectibles,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem,
                        )
                    }
                    is CollectibleScreenUiState.MonthlyView -> {
                        MonthlyCollectibleContents(
                            collectibles = collectibles,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OverallCollectibleContents(
    collectibles: List<Collectible>,
    onClickCollectibleItem: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NkAnimatedCircularProgress(
            progress = collectibles.calculateProgress(),
            description = stringResource(id = R.string.progress_rate)
        )
        Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
        CollectibleList(
            collectibles = collectibles,
            onClickCollectibleItem = onClickCollectibleItem,
        )
    }
}

@Composable
fun MonthlyCollectibleContents(
    collectibles: List<Collectible>,
    onClickCollectibleItem: (Int) -> Unit,
) {
    /*Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyRow {
            items() {

            }
        }
        NkAnimatedCircularProgress(
            progress = 0.6f, // TODO
            description = stringResource(id = R.string.progress_rate)
        )
        Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
        // TODO: normal view or time view
    }*/
}

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
    Box(
        modifier = Modifier
            .size(CollectibleItemSize)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GlideImage(
                modifier = Modifier.size(CollectibleItemSize * 0.5f),
                model = item.imageUrl,
                contentDescription = item.name,
            )
            NkText(
                text = item.name,
                style = NkTheme.typography.bodySmall,
                maxLines = 1,
                fontWeight = if (item.isCollected) FontWeight.Bold else FontWeight.Normal
            )
        }

        if (item.isCollected) {
            Icon(
                modifier = Modifier.align(Alignment.TopEnd),
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = NkTheme.colorScheme.secondary,
            )
        }
    }
}