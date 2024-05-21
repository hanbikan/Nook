package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.ChipGroup
import com.hanbikan.nook.core.designsystem.component.ChipItem
import com.hanbikan.nook.core.designsystem.component.NkChipGroup
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.domain.model.Collectible

@Composable
fun MonthlyCollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: CollectibleViewModel = hiltViewModel(),
) {
    val monthlyCollectibles = viewModel.collectibleList.collectAsStateWithLifecycle().value
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
                            collectibles = monthlyCollectibles,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem,
                        )
                    }
                    is CollectibleScreenUiState.MonthlyView -> {
                        MonthlyCollectibleContents(
                            collectibles = monthlyCollectibles,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem,
                        )
                    }
                }
            }
        }
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