package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkChipGroup
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens

@Composable
fun MonthlyCollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: MuseumViewModel = hiltViewModel(),
) {
    val collectibles = viewModel.fishList.collectAsStateWithLifecycle().value
    val viewTypeChipGroup = viewModel.viewTypeChipGroup.collectAsStateWithLifecycle().value

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.backAppBarIcon(onClick = navigateUp)
                ),
            )

            Column(
                modifier = Modifier.padding(Dimens.SideMargin, 0.dp)
            ) {
                NkChipGroup(
                    chipGroup = viewTypeChipGroup,
                    isLarge = true,
                    onClickItem = viewModel::onClickViewType,
                )

                CollectibleList(collectibles = collectibles)
            }
        }
    }
}