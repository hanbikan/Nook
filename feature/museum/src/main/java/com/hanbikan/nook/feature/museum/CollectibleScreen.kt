package com.hanbikan.nook.feature.museum

import android.view.MotionEvent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.ChipGroup
import com.hanbikan.nook.core.designsystem.component.ChipItem
import com.hanbikan.nook.core.designsystem.component.FadeAnimatedVisibility
import com.hanbikan.nook.core.designsystem.component.NkAnimatedCircularProgress
import com.hanbikan.nook.core.designsystem.component.NkChipGroup
import com.hanbikan.nook.core.designsystem.component.NkDialog
import com.hanbikan.nook.core.designsystem.component.NkTag
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.component.NkTopBackgroundGradient
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.common.HasShadowMovement
import com.hanbikan.nook.core.domain.model.common.HasShadowSize
import com.hanbikan.nook.core.domain.model.common.LocationBased
import com.hanbikan.nook.core.domain.model.common.Monthly
import com.hanbikan.nook.core.domain.model.common.calculateProgress
import com.hanbikan.nook.core.domain.model.common.convertToTimeRanges
import com.hanbikan.nook.feature.museum.CollectibleScreenUiState.MonthlyView.HourView.Companion.ALL_DAY_KEY
import com.hanbikan.nook.feature.museum.util.displayMonth
import com.hanbikan.nook.feature.museum.util.formatTime
import com.hanbikan.nook.feature.museum.util.getMonthList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val CollectibleItemWidth = 90.dp
val CollectibleItemHeight = 80.dp
private val GradientHeight = Dimens.SpacingMedium

@Composable
fun CollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: CollectibleViewModel = hiltViewModel(),
    // TODO: isMonthly -> false일 경우 chip group 제거(화석 등으로 확장 시)
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val isHuntingMode = viewModel.isHuntingMode.collectAsStateWithLifecycle().value
    val isNorth = viewModel.isNorth.collectAsStateWithLifecycle().value

    val collectibleForDetailCollectibleDialog = viewModel.collectibleForDetailCollectibleDialog.collectAsStateWithLifecycle().value
    val collectibleForCollectDialog = viewModel.collectibleForCollectDialog.collectAsStateWithLifecycle().value
    val isInfoDialogShown = viewModel.isInfoDialogShown.collectAsStateWithLifecycle().value

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            CollectibleScreenTopAppBar(navigateUp, viewModel, isHuntingMode, uiState)

            // Contents
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
                        onClickItem = viewModel::onClickViewTypeChip,
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
                            isHuntingMode = isHuntingMode,
                            isNorth = isNorth,
                        )
                    }

                    is CollectibleScreenUiState.MonthlyView -> {
                        MonthlyCollectibleContents(
                            uiState = uiState,
                            onClickMonth = viewModel::onClickMonth,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem,
                            onLongClickCollectibleItem = viewModel::onLongClickCollectibleItem,
                            isHuntingMode = isHuntingMode,
                        )
                    }
                }
            }
        }

        // Dialogs
        DetailCollectibleDialog(
            collectible = collectibleForDetailCollectibleDialog,
            onDismiss = viewModel::onDismissDetailCollectibleDialog,
            isNorth = isNorth,
        )

        NkDialog(
            visible = isInfoDialogShown,
            description = stringResource(id = R.string.collectible_screen_info),
            onDismissRequest = viewModel::switchIsInfoDialogShown,
            onConfirmation = viewModel::switchIsInfoDialogShown,
            hasOnlyConfirmationButton = true
        )

        NkDialog(
            visible = collectibleForCollectDialog != null,
            description = stringResource(id = R.string.collect_item, collectibleForCollectDialog?.name?:""),
            onDismissRequest = viewModel::onDismissCollectDialog,
            onConfirmation = viewModel::onConfirmCollectDialog
        )
    }
}

@Composable
private fun CollectibleScreenTopAppBar(
    navigateUp: () -> Unit,
    viewModel: CollectibleViewModel,
    isHuntingMode: Boolean,
    uiState: CollectibleScreenUiState
) {
    var isSortMenuExpanded by remember { mutableStateOf(false) }
    var isFilterMenuExpanded by remember { mutableStateOf(false) }

    val rightAppBarIcons: ArrayList<AppBarIcon> = arrayListOf()
    if (uiState is CollectibleScreenUiState.MonthlyView) {
        rightAppBarIcons.add(
            if (uiState is CollectibleScreenUiState.MonthlyView.GeneralView) {
                AppBarIcon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_grid_4x4_24),
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
        )
    }
    rightAppBarIcons.add(
        AppBarIcon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_info_24),
            contentDescription = stringResource(id = R.string.info),
            onClick = viewModel::switchIsInfoDialogShown
        )
    )
    rightAppBarIcons.add(
        AppBarIcon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_sort_24),
            contentDescription = stringResource(id = R.string.sort_by),
            onClick = { isSortMenuExpanded = true },
            DropDownMenu = {
                DropdownMenu(
                    expanded = isSortMenuExpanded,
                    onDismissRequest = { isSortMenuExpanded = false },
                ) {
                    viewModel.collectibleSorts.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = it.nameResourceId))
                            },
                            onClick = {
                                viewModel.setSort(it)
                                isSortMenuExpanded = false
                            }
                        )
                    }
                }
            }
        )
    )
    rightAppBarIcons.add(
        AppBarIcon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_filter_alt_24),
            contentDescription = stringResource(id = R.string.filter_by),
            onClick = { isFilterMenuExpanded = true },
            DropDownMenu = {
                DropdownMenu(
                    expanded = isFilterMenuExpanded,
                    onDismissRequest = { isFilterMenuExpanded = false },
                ) {
                    viewModel.collectibleFilters.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = it.nameResourceId))
                            },
                            onClick = {
                                viewModel.setFilter(it)
                                isFilterMenuExpanded = false
                            }
                        )
                    }
                }
            }
        )
    )
    rightAppBarIcons.add(
        AppBarIcon(
            imageVector = ImageVector.vectorResource(id = if (!isHuntingMode) R.drawable.baseline_explore_off_24 else R.drawable.baseline_explore_24),
            contentDescription = stringResource(id = R.string.hunting_mode),
            onClick = viewModel::switchIsHuntingMode
        )
    )

    NkTopAppBar(
        leftAppBarIcons = listOf(
            AppBarIcon.backAppBarIcon(onClick = navigateUp)
        ),
        rightAppBarIcons = rightAppBarIcons,
    )
}


/**
 * 전체
 */
@Composable
fun OverallCollectibleContents(
    collectibles: List<Collectible>,
    onClickCollectibleItem: (Collectible) -> Unit,
    onLongClickCollectibleItem: (Collectible) -> Unit,
    isHuntingMode: Boolean,
    isNorth: Boolean,
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
                        isHuntingMode = isHuntingMode,
                        isNorth = isNorth,
                    )
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
    isHuntingMode: Boolean,
    isNorth: Boolean,
) {
    Row {
        rowItems.forEach { item ->
            CollectibleItem(
                item = item,
                isHuntingMode = isHuntingMode,
                isNorth = isNorth,
                onClick = { onClickCollectibleItem(item) },
                onLongClick = { onLongClickCollectibleItem(item) },
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
    isHuntingMode: Boolean,
    isNorth: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val imageUrl = if (!isHuntingMode) item.imageUrl else item.renderUrl

    Box(
        modifier = Modifier
            .width(CollectibleItemWidth)
            .padding(vertical = Dimens.SpacingSmall)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GlideImage(
                modifier = Modifier.size(CollectibleItemHeight * 0.5f),
                model = imageUrl,
                contentDescription = item.name,
            )
            NkText(
                text = item.name,
                style = NkTheme.typography.bodySmall,
                maxLines = 1,
                fontWeight = if (item.isCollected) FontWeight.Bold else FontWeight.Normal
            )
            if (isHuntingMode) {
                if (item is Monthly) {
                    val timeRanges = item.getCurrentMonthToTimes(isNorth).convertToTimeRanges()
                    val displayMonths = timeRanges.map { it.displayMonth() }.joinToString(", ")
                    NkText(
                        text = displayMonths,
                        style = NkTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                    )
                }
                if (item is LocationBased) {
                    NkText(
                        text = item.location,
                        style = NkTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                    )
                }
                if (item is HasShadowSize) {
                    NkText(
                        text = item.shadowSize,
                        style = NkTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                    )
                }
                if (item is HasShadowMovement) {
                    NkText(
                        text = item.shadowMovement,
                        style = NkTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                    )
                }
            }
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


/**
 * 월별
 */
@Composable
fun MonthlyCollectibleContents(
    uiState: CollectibleScreenUiState.MonthlyView,
    onClickMonth: (Int) -> Unit,
    onClickCollectibleItem: (Collectible) -> Unit,
    onLongClickCollectibleItem: (Collectible) -> Unit,
    isHuntingMode: Boolean,
) {
    Column {
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))

        NkChipGroup(
            paddingValues = PaddingValues(horizontal = Dimens.SideMargin),
            chipGroup = ChipGroup(
                chipItems = getMonthList().map { ChipItem(it) },
                selectedIndex = uiState.month - 1 // 0-index임에 유의
            ),
            autoScroll = true,
            onClickItem = { index -> onClickMonth(index + 1) },
        )

        when (uiState) {
            // 일반 뷰
            is CollectibleScreenUiState.MonthlyView.GeneralView -> {
                OverallCollectibleContents(
                    collectibles = uiState.collectibleListForMonth,
                    onClickCollectibleItem = onClickCollectibleItem,
                    onLongClickCollectibleItem = onLongClickCollectibleItem,
                    isHuntingMode = isHuntingMode,
                    isNorth = uiState.isNorth
                )
            }

            // 시간 뷰
            is CollectibleScreenUiState.MonthlyView.HourView -> {
                HourViewContents(
                    uiState = uiState,
                    onClickCollectibleItem = onClickCollectibleItem,
                    onLongClickCollectibleItem = onLongClickCollectibleItem,
                    isHuntingMode = isHuntingMode,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HourViewContents(
    uiState: CollectibleScreenUiState.MonthlyView.HourView,
    onClickCollectibleItem: (Collectible) -> Unit,
    onLongClickCollectibleItem: (Collectible) -> Unit,
    isHuntingMode: Boolean,
) {
    val lazyListState = rememberLazyListState()
    var containerWidth by remember { mutableIntStateOf(0) }
    val itemWidth: Float = with(LocalDensity.current) { CollectibleItemWidth.toPx() }
    val itemsPerRow: Int = (containerWidth / itemWidth).toInt()
    val density: Density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    var isIndexScrollBarShown by remember { mutableStateOf(false) }

    LaunchedEffect(itemsPerRow, uiState.month) {
        if (itemsPerRow > 0) {
            val currentHourKey: Int = uiState.getCurrentHourKey()
            val scrollIndex = uiState.getScrollIndexForKey(currentHourKey, itemsPerRow)
            lazyListState.scrollToItem(
                index = scrollIndex,
                scrollOffset = with(density) { -CollectibleItemHeight.toPx() }.toInt()
            )
        }
    }

    // 일정 시간 동안 스크롤이 없거나 스크롤바를 조작하지 않을 경우 스크롤바를 숨김
    LaunchedEffect(isIndexScrollBarShown) {
        if (isIndexScrollBarShown) {
            delay(2000)
            isIndexScrollBarShown = false
        }
    }

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { containerWidth = it.size.width }
                .pointerInteropFilter {
                    isIndexScrollBarShown =
                        it.action == MotionEvent.ACTION_DOWN || it.action == MotionEvent.ACTION_MOVE
                    false
                },
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(GradientHeight))
            }
            uiState.startHourToCollectibleListForMonth.forEach { (startHour, collectibleList) ->
                if (itemsPerRow > 0) {
                    TimeAndCollectibleItems(
                        uiState = uiState,
                        startHour = startHour,
                        collectibleList = collectibleList,
                        itemsPerRow = itemsPerRow,
                        onClickCollectibleItem = onClickCollectibleItem,
                        onLongClickCollectibleItem = onLongClickCollectibleItem,
                        isHuntingMode = isHuntingMode,
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            HourViewIndexScrollBar(
                uiState = uiState,
                visible = isIndexScrollBarShown,
                setVisible = { isIndexScrollBarShown = it },
                scrollByHourKey = {
                    coroutineScope.launch {
                        val index = uiState.getScrollIndexForKey(it, itemsPerRow)
                        lazyListState.scrollToItem(index, 0)
                    }
                }
            )
        }
        NkTopBackgroundGradient(height = GradientHeight)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HourViewIndexScrollBar(
    uiState: CollectibleScreenUiState.MonthlyView.HourView,
    visible: Boolean = true,
    setVisible: (Boolean) -> Unit,
    scrollByHourKey: (Int) -> Unit,
) {
    val startHours = uiState.startHourToCollectibleListForMonth.keys.sorted()
    val endYListForIndex = MutableList(uiState.startHourToCollectibleListForMonth.keys.count()) { 0.0f }

    FadeAnimatedVisibility(visible = visible) {
        Column(
            modifier = Modifier
                .padding(Dimens.SpacingSmall)
                .pointerInteropFilter {
                    // 터치 Y 좌표와 비교하여 해당 hourKey 찾기
                    if (it.action == MotionEvent.ACTION_MOVE) {
                        val matchingIndex = endYListForIndex.indexOfFirst { endY -> it.y <= endY }
                        if (matchingIndex != -1) {
                            val matchingHourKey = startHours[matchingIndex]
                            scrollByHourKey(matchingHourKey)
                        } else {
                            scrollByHourKey(startHours.last())
                        }
                    }

                    setVisible(it.action == MotionEvent.ACTION_DOWN || it.action == MotionEvent.ACTION_MOVE)

                    true
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium),
        ) {
            startHours.forEachIndexed { index, startHour ->
                NkText(
                    text = if (startHour == ALL_DAY_KEY) "@" else "$startHour",
                    color = if (!uiState.isStartHourCurrentHourRange(startHour)) NkTheme.colorScheme.primaryContainer else NkTheme.colorScheme.background,
                    style = NkTheme.typography.bodySmall,
                    modifier = Modifier
                        .background(
                            color = if (!uiState.isStartHourCurrentHourRange(startHour)) Color.Transparent else NkTheme.colorScheme.primary,
                            shape = RoundedCornerShape(Dimens.SpacingSmall),
                        )
                        .padding(Dimens.SpacingExtraSmall)
                        .onGloballyPositioned { coordinates ->
                            // Text end Y 좌표 저장
                            val centerY: Float =
                                coordinates.positionInParent().y + coordinates.size.height
                            if (index < endYListForIndex.size) {
                                endYListForIndex[index] = centerY
                            } else {
                                // Exception
                            }
                        }
                )
            }
        }
    }
}


fun LazyListScope.TimeAndCollectibleItems(
    uiState: CollectibleScreenUiState.MonthlyView.HourView,
    startHour: Int,
    collectibleList: List<Collectible>,
    itemsPerRow: Int,
    onClickCollectibleItem: (Collectible) -> Unit,
    onLongClickCollectibleItem: (Collectible) -> Unit,
    isHuntingMode: Boolean,
) {
    // 시간
    item {
        val endHour = uiState.getEndHourByStartHour(startHour)
        val text = if (startHour == ALL_DAY_KEY) {
            stringResource(id = R.string.all_day)
        } else {
            formatTime(startHour, endHour)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.SideMargin),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NkText(
                style = NkTheme.typography.titleLarge,
                text = text,
            )
            // 현재 시간 범위일 경우 '현재' 박스 표시
            if (uiState.isStartHourCurrentHourRange(startHour)) {
                Spacer(modifier = Modifier.width(Dimens.SpacingSmall))
                NkTag(text = stringResource(id = R.string.now))
            }
        }
    }

    // 아이템 리스트
    if (collectibleList.isNotEmpty()) {
        itemsIndexed(collectibleList.chunked(itemsPerRow)) { _, rowItems ->
            CollectibleItemsForRow(
                rowItems = rowItems,
                onClickCollectibleItem = onClickCollectibleItem,
                onLongClickCollectibleItem = onLongClickCollectibleItem,
                itemsPerRow = itemsPerRow,
                isHuntingMode = isHuntingMode,
                isNorth = uiState.isNorth,
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