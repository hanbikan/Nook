package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkAnimatedCircularProgress
import com.hanbikan.nook.core.designsystem.component.NkChipGroup
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.domain.model.Collectible

@Composable
fun CollectibleScreen(
    navigateUp: () -> Unit,
    viewModel: MuseumViewModel = hiltViewModel(),
) {
    val collectibles = viewModel.fishList.collectAsStateWithLifecycle().value

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.backAppBarIcon(onClick = navigateUp)
                ),
            )

            Column(
                modifier = Modifier.padding(Dimens.SideMargin, 0.dp),
            ) {
                OverallCollectibleContents(collectibles = collectibles)
            }
        }
    }
}

@Composable
fun OverallCollectibleContents(
    collectibles: List<Collectible>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NkAnimatedCircularProgress(
            progress = 0.6f, // TODO
            description = stringResource(id = R.string.progress_rate)
        )
        Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
        CollectibleList(collectibles = collectibles)
    }
}