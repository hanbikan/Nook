package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
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
import com.hanbikan.nook.core.designsystem.component.NkTopBackgroundGradient
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Collectible
import com.hanbikan.nook.core.domain.model.calculateProgress


private val CollectibleItemWidth = 90.dp
private val CollectibleItemHeight = 80.dp
private val GradientHeight = Dimens.SpacingMedium

@Composable
fun CollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: CollectibleViewModel = hiltViewModel(),
    // TODO: isMonthly? -> false일 경우 chip group 제거
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.backAppBarIcon(onClick = navigateUp)
                ),
                // TODO: MonthlyView -> 일반 정렬 or time 정렬
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.chipIndex != null) {
                    NkChipGroup(
                        modifier = Modifier.padding(horizontal = Dimens.SideMargin),
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
                            collectibles = uiState.collectibleList,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem,
                        )
                    }

                    is CollectibleScreenUiState.MonthlyView -> {
                        MonthlyCollectibleContents(
                            uiState = uiState,
                            onClickMonth = viewModel::onClickMonth,
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
    onClickCollectibleItem: (Collectible) -> Unit,
) {
    var containerWidth by remember { mutableIntStateOf(0) }
    val itemWidth = with(LocalDensity.current) { CollectibleItemWidth.toPx() }
    val itemsPerRow = (containerWidth / itemWidth).toInt()

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { containerWidth = it.size.width },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(GradientHeight))
                NkAnimatedCircularProgress(
                    progress = collectibles.calculateProgress(),
                    description = stringResource(id = R.string.progress_rate)
                )
                Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
            }
            if (itemsPerRow > 0) {
                itemsIndexed(collectibles.chunked(itemsPerRow)) { rowIndex, rowItems ->
                    Row {
                        rowItems.forEach { item ->
                            CollectibleItem(
                                item = item,
                                onClick = { onClickCollectibleItem(item) }
                            )
                        }
                        if (rowItems.count() < itemsPerRow) {
                            repeat(itemsPerRow - rowItems.count()) {
                                Box(modifier = Modifier.width(CollectibleItemWidth))
                            }
                        }
                    }
                }
            }
        }

        NkTopBackgroundGradient(height = GradientHeight)
    }
}

@Composable
fun MonthlyCollectibleContents(
    uiState: CollectibleScreenUiState.MonthlyView,
    onClickMonth: (Int) -> Unit,
    onClickCollectibleItem: (Collectible) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))

        NkChipGroup(
            paddingValues = PaddingValues(horizontal = Dimens.SideMargin),
            chipGroup = ChipGroup(
                chips = listOf(
                    ChipItem(stringResource(id = R.string.january)),
                    ChipItem(stringResource(id = R.string.february)),
                    ChipItem(stringResource(id = R.string.march)),
                    ChipItem(stringResource(id = R.string.april)),
                    ChipItem(stringResource(id = R.string.may)),
                    ChipItem(stringResource(id = R.string.june)),
                    ChipItem(stringResource(id = R.string.july)),
                    ChipItem(stringResource(id = R.string.august)),
                    ChipItem(stringResource(id = R.string.september)),
                    ChipItem(stringResource(id = R.string.october)),
                    ChipItem(stringResource(id = R.string.november)),
                    ChipItem(stringResource(id = R.string.december)),
                ),
                selectedIndex = uiState.month - 1 // 0-index임에 유의
            ),
            autoScroll = true,
            onClickItem = { index -> onClickMonth(index + 1) },
        )

        when (uiState) {
            is CollectibleScreenUiState.MonthlyView.GeneralView -> {
                OverallCollectibleContents(
                    collectibles = uiState.collectibleList,
                    onClickCollectibleItem = onClickCollectibleItem,
                )
            }

            is CollectibleScreenUiState.MonthlyView.HourView -> {
                // TODO: hour view
            }
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
            .width(CollectibleItemWidth)
            .height(CollectibleItemHeight)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GlideImage(
                modifier = Modifier.size(CollectibleItemHeight * 0.5f),
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