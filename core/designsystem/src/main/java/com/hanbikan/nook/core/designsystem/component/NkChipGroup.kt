package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

data class ChipGroup(
    val chipItems: List<ChipItem>,
    val selectedIndex: Int = 0,
) {
    fun copyWithIndex(index: Int) = this.copy(selectedIndex = index)
}

data class ChipItem(
    val text: String
)

@Composable
fun NkChipGroup(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    chipGroup: ChipGroup,
    isLarge: Boolean = false,
    autoScroll: Boolean = false,
    primaryColor: Color = NkTheme.colorScheme.primary,
    background: Color = NkTheme.colorScheme.background,
    onClickItem: (Int) -> Unit,
) {
    val selectedIndex = chipGroup.selectedIndex
    val listState = rememberLazyListState()

    if (autoScroll) {
        LaunchedEffect(chipGroup) {
            val listWidth = listState.layoutInfo.viewportEndOffset - listState.layoutInfo.viewportStartOffset
            val itemWidth = listState.layoutInfo.visibleItemsInfo.first().size
            listState.animateScrollToItem(
                index = chipGroup.selectedIndex,
                scrollOffset = -(listWidth / 2 - itemWidth) // 중앙에 배치되게
            )
        }
    }

    LazyRow(
        modifier = modifier,
        contentPadding = paddingValues,
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall),
        state = listState,
    ) {
        itemsIndexed(chipGroup.chipItems) { index, item ->
            NkChipItem(
                text = item.text,
                selected = index == selectedIndex,
                isLarge = isLarge,
                primaryColor = primaryColor,
                background = background,
                onClick = { onClickItem(index) },
            )
        }
    }
}

@Composable
fun NkChipItem(
    text: String,
    selected: Boolean,
    isLarge: Boolean,
    primaryColor: Color = NkTheme.colorScheme.primary,
    background: Color = NkTheme.colorScheme.background,
    onClick: () -> Unit,
) {
    val radiusShape = RoundedCornerShape(Dimens.SpacingExtraLarge)

    Box(
        modifier = Modifier
            .background(
                color = if (selected) primaryColor else background,
                shape = radiusShape
            )
            .border(
                border = BorderStroke(1.dp, primaryColor),
                shape = radiusShape
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = if (isLarge) Dimens.SpacingLarge else Dimens.SpacingMedium,
                vertical = if (isLarge) Dimens.SpacingMedium else Dimens.SpacingSmall
            ),
        contentAlignment = Alignment.Center
    ) {
        NkText(
            text = text,
            color = if (selected) background else primaryColor
        )
    }
}

@Composable
@Preview
fun NkChipGroupPreview() {
    NkChipGroup(
        chipGroup = ChipGroup(
            chipItems = listOf(
                ChipItem("Apple"),
                ChipItem("Banana"),
                ChipItem("Cream"),
            )
        ),
        isLarge = true,
        onClickItem = {}
    )
}
