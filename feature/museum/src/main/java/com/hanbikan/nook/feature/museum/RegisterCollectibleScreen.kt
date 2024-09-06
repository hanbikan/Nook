package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.FadeAnimatedVisibility
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.ui.TRANSITION_DURATION
import kotlinx.coroutines.delay

private val ITEM_SIZE = 60.dp

@Composable
fun RegisterCollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: RegisterCollectibleViewModel = hiltViewModel(),
) {
    val bugs = viewModel.bugs.collectAsStateWithLifecycle().value
    val fishes = viewModel.fishes.collectAsStateWithLifecycle().value
    val seaCreatures = viewModel.seaCreatures.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    val scrollState = rememberScrollState()
    var showsScreen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(TRANSITION_DURATION.toLong())
        showsScreen = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        FadeAnimatedVisibility(visible = showsScreen) {
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
                            onClickCollectibleItem = viewModel::onClickCollectibleItem
                        )

                        RegisterCollectibleItems(
                            collectibles = fishes,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem
                        )

                        RegisterCollectibleItems(
                            collectibles = seaCreatures,
                            onClickCollectibleItem = viewModel::onClickCollectibleItem
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RegisterCollectibleItems(
    collectibles: List<Collectible>,
    onClickCollectibleItem: (Collectible) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.SpacingSmall)
            .border(1.dp, NkTheme.colorScheme.primaryContainer)
    ) {
        items(collectibles.chunked(5)) { collectiblesOnColumn ->
            Column {
                collectiblesOnColumn.forEach { item ->
                    val iconId = if (item is Bug) {
                        R.drawable.butterfly
                    } else if (item is Fish) {
                        R.drawable.shark
                    } else {
                        R.drawable.shrimp
                    }

                    Box(
                        modifier = Modifier
                            .size(ITEM_SIZE)
                            .border(0.5.dp, NkTheme.colorScheme.primaryContainer)
                            .clickable { onClickCollectibleItem(item) },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (item.isCollected) {
                            GlideImage(
                                model = item.imageUrl,
                                contentDescription = item.name
                            )
                        } else {
                            Image(
                                imageVector = ImageVector.vectorResource(id = iconId),
                                contentDescription = null,
                                modifier = Modifier.size(ITEM_SIZE * 0.25f),
                                colorFilter = ColorFilter.tint(NkTheme.colorScheme.primaryContainer),
                            )
                        }
                    }
                }
            }
        }
    }
}