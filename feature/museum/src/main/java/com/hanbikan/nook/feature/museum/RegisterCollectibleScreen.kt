package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.FadeAnimatedVisibility
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.common.Collectible

@Composable
fun RegisterCollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: RegisterCollectibleViewModel = hiltViewModel(),
) {
    val bugs = viewModel.bugs.collectAsStateWithLifecycle().value
    val fishes = viewModel.fishes.collectAsStateWithLifecycle().value
    val seaCreatures = viewModel.seaCreatures.collectAsStateWithLifecycle().value
    val isNorth = viewModel.isNorth.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    val scrollState = rememberScrollState()

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.backAppBarIcon(onClick = navigateUp)
                ),
            )

            FadeAnimatedVisibility(visible = !isLoading) {
                bugs!!
                fishes!!
                seaCreatures!!

                Column(
                    modifier = Modifier.padding(Dimens.SideMargin),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
                ) {
                    NkText(
                        text = stringResource(id = R.string.register_collectible_title),
                        style = NkTheme.typography.headlineLarge,
                    )

                    NkText(
                        text = stringResource(id = R.string.register_collectible_description),
                        style = NkTheme.typography.bodyLarge,
                    )

                    RegisterCollectibleItems(
                        collectibles = bugs,
                        isNorth = isNorth,
                        onClickCollectibleItem = viewModel::onClickCollectibleItem
                    )

                    RegisterCollectibleItems(
                        collectibles = fishes,
                        isNorth = isNorth,
                        onClickCollectibleItem = viewModel::onClickCollectibleItem
                    )

                    RegisterCollectibleItems(
                        collectibles = seaCreatures,
                        isNorth = isNorth,
                        onClickCollectibleItem = viewModel::onClickCollectibleItem
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterCollectibleItems(
    collectibles: List<Collectible>,
    isNorth: Boolean,
    onClickCollectibleItem: (Collectible) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .background(
                NkTheme.colorScheme.onBackground,
                RoundedCornerShape(Dimens.SpacingMedium)
            )
            .fillMaxWidth()
    ) {
        items(collectibles.chunked(5)) { collectiblesOnColumn ->
            Column {
                collectiblesOnColumn.forEach { item ->
                    CollectibleItem(
                        item = item,
                        isHuntingMode = false,
                        isNorth = isNorth,
                        onClick = { onClickCollectibleItem(item) },
                        onLongClick = {}
                    )
                }
            }
        }
    }
}