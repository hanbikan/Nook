package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.hanbikan.nook.core.designsystem.component.NkDialogWithContents
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTextButton
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.component.NkTopBackgroundGradient
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Collectible
import com.hanbikan.nook.core.domain.model.LocationBased
import com.hanbikan.nook.core.domain.model.calculateProgress
import com.hanbikan.nook.feature.museum.CollectibleScreenUiState.MonthlyView.HourView.Companion.ALL_DAY_KEY
import kotlin.math.ceil

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
    val collectibleToShowInDialog =
        viewModel.collectibleToShowInDialog.collectAsStateWithLifecycle().value

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.backAppBarIcon(onClick = navigateUp)
                ),
                rightAppBarIcons = if (uiState is CollectibleScreenUiState.MonthlyView) {
                    val icon = if (uiState is CollectibleScreenUiState.MonthlyView.GeneralView) {
                        AppBarIcon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ascending_sort),
                            contentDescription = stringResource(id = R.string.general_view),
                            onClick = viewModel::onClickMonthlyViewType
                        )
                    } else {
                        AppBarIcon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.time),
                            contentDescription = stringResource(id = R.string.hour_view),
                            onClick = viewModel::onClickMonthlyViewType
                        )
                    }
                    listOf(icon)
                } else {
                    listOf()
                }
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.chipIndex != null) {
                    NkChipGroup(
                        modifier = Modifier.padding(horizontal = Dimens.SideMargin),
                        chipGroup = ChipGroup(
                            chipItems = listOf(
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
                            onLongClickCollectibleItem = viewModel::onLongClickCollectibleItem,
                        )
                    }

                    is CollectibleScreenUiState.MonthlyView -> {
                        MonthlyCollectibleContents(
                            uiState = uiState,
                            onClickMonth = viewModel::onClickMonth,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem,
                            onLongClickCollectibleItem = viewModel::onLongClickCollectibleItem,
                        )
                    }
                }
            }
        }

        CollectibleDialog(
            collectible = collectibleToShowInDialog,
            onDismiss = viewModel::onDismissCollectibleDialog,
        )
    }
}

@Composable
fun OverallCollectibleContents(
    collectibles: List<Collectible>,
    onClickCollectibleItem: (Collectible) -> Unit,
    onLongClickCollectibleItem: (Collectible) -> Unit,
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
                itemsIndexed(collectibles.chunked(itemsPerRow)) { _, rowItems ->
                    CollectibleItemsForRow(
                        rowItems = rowItems,
                        onClickCollectibleItem = onClickCollectibleItem,
                        onLongClickCollectibleItem = onLongClickCollectibleItem,
                        itemsPerRow = itemsPerRow,
                    )
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
    onLongClickCollectibleItem: (Collectible) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))

        NkChipGroup(
            paddingValues = PaddingValues(horizontal = Dimens.SideMargin),
            chipGroup = ChipGroup(
                chipItems = listOf(
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
                    collectibles = uiState.collectibleListForMonth,
                    onClickCollectibleItem = onClickCollectibleItem,
                    onLongClickCollectibleItem = onLongClickCollectibleItem
                )
            }

            is CollectibleScreenUiState.MonthlyView.HourView -> {
                HourViewContents(
                    uiState = uiState,
                    onClickCollectibleItem = onClickCollectibleItem,
                    onLongClickCollectibleItem = onLongClickCollectibleItem
                )
            }
        }
    }
}

@Composable
fun HourViewContents(
    uiState: CollectibleScreenUiState.MonthlyView.HourView,
    onClickCollectibleItem: (Collectible) -> Unit,
    onLongClickCollectibleItem: (Collectible) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    var containerWidth by remember { mutableIntStateOf(0) }
    val itemWidth = with(LocalDensity.current) { CollectibleItemWidth.toPx() }
    val itemsPerRow = (containerWidth / itemWidth).toInt()
    val density = LocalDensity.current

    LaunchedEffect(itemsPerRow, uiState.month) {
        if (itemsPerRow > 0) {
            val keyForCurrentHour: Int = uiState.getKeyForCurrentHour()
            var scrollIndex = 1
            uiState.startHourToCollectibleListForMonth.forEach { (startHour, collectibleList) ->
                if (startHour < keyForCurrentHour) {
                    scrollIndex += 2 + ceil(
                        (collectibleList.count().toFloat() / itemsPerRow)
                    ).toInt()
                }
            }
            lazyListState.animateScrollToItem(
                index = scrollIndex,
                scrollOffset = with(density) { -CollectibleItemHeight.toPx() }.toInt()
            )
        }
    }

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { containerWidth = it.size.width },
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(GradientHeight))
            }
            uiState.startHourToCollectibleListForMonth.forEach { (startHour, collectibleList) ->
                if (itemsPerRow > 0) {
                    item {
                        val text = if (startHour == ALL_DAY_KEY) {
                            stringResource(id = R.string.all_day)
                        } else {
                            formatTime(startHour, uiState.getEndHourByStartHour(startHour))
                        }
                        NkText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimens.SideMargin),
                            style = NkTheme.typography.titleLarge,
                            text = text,
                        )
                    }
                    if (collectibleList.isNotEmpty()) {
                        itemsIndexed(collectibleList.chunked(itemsPerRow)) { _, rowItems ->
                            CollectibleItemsForRow(
                                rowItems = rowItems,
                                onClickCollectibleItem = onClickCollectibleItem,
                                onLongClickCollectibleItem = onLongClickCollectibleItem,
                                itemsPerRow = itemsPerRow,
                            )
                        }
                    } else {
                        // 시간대에 잡을 수 있는 아이템이 없는 경우
                        item {
                            Box(
                                modifier = Modifier.height(CollectibleItemHeight),
                                contentAlignment = Alignment.Center,
                            ) {
                                NkText(
                                    text = stringResource(id = R.string.empty),
                                    color = NkTheme.colorScheme.primaryContainer,
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                    }
                }
            }
        }

        NkTopBackgroundGradient(height = GradientHeight)
    }
}

@Composable
fun CollectibleItemsForRow(
    rowItems: List<Collectible>,
    onClickCollectibleItem: (Collectible) -> Unit,
    onLongClickCollectibleItem: (Collectible) -> Unit,
    itemsPerRow: Int,
) {
    Row {
        rowItems.forEach { item ->
            CollectibleItem(
                item = item,
                onClick = { onClickCollectibleItem(item) },
                onLongClick = { onLongClickCollectibleItem(item) }
            )
        }
        if (rowItems.count() < itemsPerRow) {
            repeat(itemsPerRow - rowItems.count()) {
                Box(modifier = Modifier.width(CollectibleItemWidth))
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun CollectibleItem(
    item: Collectible,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(CollectibleItemWidth)
            .height(CollectibleItemHeight)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CollectibleDialog(
    collectible: Collectible?,
    onDismiss: () -> Unit,
) {
    NkDialogWithContents(
        visible = collectible != null,
        onDismissRequest = onDismiss
    ) {
        collectible?.let { item ->
            GlideImage(
                modifier = Modifier.size(CollectibleItemHeight * 0.5f),
                model = item.imageUrl,
                contentDescription = item.name,
            )

            Column {
                NkText(text = stringResource(id = R.string.collectible_name, collectible.name))
                if (collectible is LocationBased) {
                    NkText(text = stringResource(id = R.string.collectible_location, collectible.location))
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

// formatTime(3, 6) returns "03:00~05:59"
private fun formatTime(startHour: Int, endHour: Int): String {
    return String.format("%02d:00~%02d:59", startHour, endHour - 1)
}