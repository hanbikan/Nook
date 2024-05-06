package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

data class ChipGroup(
    val chips: List<ChipItem>,
    val selectedIndex: Int = 0,
) {
    fun copyWithIndex(index: Int) = this.copy(selectedIndex = index)
}

data class ChipItem(
    val text: String
)

@Composable
fun NkChipGroup(
    chipGroup: ChipGroup,
    isLarge: Boolean = false,
    onClickItem: (Int) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall)
    ) {
        itemsIndexed(chipGroup.chips) { index, item ->
            NkChipItem(
                text = item.text,
                selected = index == chipGroup.selectedIndex,
                isLarge = isLarge,
                onClick = { onClickItem(index) }
            )
        }
    }
}

@Composable
fun NkChipItem(
    text: String,
    selected: Boolean,
    isLarge: Boolean,
    onClick: () -> Unit,
) {
    val radiusShape = RoundedCornerShape(Dimens.SpacingLarge)

    Box(
        modifier = Modifier
            .background(
                color = if (selected) NkTheme.colorScheme.primary else NkTheme.colorScheme.background,
                shape = radiusShape
            )
            .border(
                border = BorderStroke(1.dp, NkTheme.colorScheme.primary),
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
            color = if (selected) NkTheme.colorScheme.background else NkTheme.colorScheme.primary
        )
    }
}

@Composable
@Preview
fun NkChipGroupPreview() {
    NkChipGroup(
        chipGroup = ChipGroup(
            chips = listOf(
                ChipItem("Apple"),
                ChipItem("Banana"),
                ChipItem("Cream"),
            )
        ),
        onClickItem = {}
    )
}
