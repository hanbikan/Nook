package com.hanbikan.nook.feature.museum

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.FadeAnimatedVisibility
import com.hanbikan.nook.core.designsystem.component.NkDialog
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.feature.museum.model.CollectibleSequence

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MuseumScreen(
    navigateToMonthlyCollectible: (CollectibleSequence) -> Unit,
    navigateToRegisterCollectible: () -> Unit,
    viewModel: MuseumViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val isNorth = viewModel.isNorth.collectAsStateWithLifecycle().value
    val hasMuseumGuideShown = viewModel.hasMuseumGuideShown.collectAsStateWithLifecycle().value
    val uncollectedForMonth = viewModel.uncollectedForMonth.collectAsStateWithLifecycle().value
    val currentlyCollectibleBugs =
        viewModel.currentlyCollectibleBugs.collectAsStateWithLifecycle().value
    val currentlyCollectibleFishes =
        viewModel.currentlyCollectibleFishes.collectAsStateWithLifecycle().value
    val currentlyCollectibleSeaCreature =
        viewModel.currentlyCollectibleSeaCreature.collectAsStateWithLifecycle().value

    val collectibleForDetailCollectibleDialog =
        viewModel.collectibleForDetailCollectibleDialog.collectAsStateWithLifecycle().value
    val collectibleForCollectDialog =
        viewModel.collectibleForCollectDialog.collectAsStateWithLifecycle().value

    val scrollState = rememberScrollState()

    val bugProgress = viewModel.bugProgress.collectAsStateWithLifecycle().value
    val fishProgress = viewModel.fishProgress.collectAsStateWithLifecycle().value
    val seaCreaturesProgress = viewModel.seaCreatureProgress.collectAsStateWithLifecycle().value

    val overallProgress = viewModel.overallProgress.collectAsStateWithLifecycle().value
    val uncollectedCountForMonth = viewModel.uncollectedCountForMonth.collectAsStateWithLifecycle().value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                rightAppBarIcons = listOf(
                    AppBarIcon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_app_registration_24),
                        contentDescription = stringResource(id = R.string.register_collectible_items),
                        onClick = navigateToRegisterCollectible
                    ),
                ),
            )

            FadeAnimatedVisibility(visible = !isLoading) {
                bugProgress!!
                fishProgress!!
                seaCreaturesProgress!!
                overallProgress!!
                currentlyCollectibleBugs!!
                currentlyCollectibleFishes!!
                currentlyCollectibleSeaCreature!!
                uncollectedCountForMonth!!
                uncollectedForMonth!!

                Column(
                    modifier = Modifier
                        .padding(horizontal = Dimens.SideMargin)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium),
                ) {
                    // 전체 수집률
                    NkText(
                        text = stringResource(
                            id = R.string.overall_progress_title,
                            (overallProgress * 100).toInt()
                        ),
                        style = NkTheme.typography.titleLarge,
                        modifier = Modifier.clickable {
                            Toast.makeText(
                                context,
                                getCollectionRateToastMessage(overallProgress, context),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    CollectionProgress(
                        name = stringResource(id = R.string.bug_progress),
                        progress = bugProgress,
                        onClick = { navigateToMonthlyCollectible(CollectibleSequence.BUG) }
                    )
                    CollectionProgress(
                        name = stringResource(id = R.string.fish_progress),
                        progress = fishProgress,
                        onClick = { navigateToMonthlyCollectible(CollectibleSequence.FISH) }
                    )
                    CollectionProgress(
                        name = stringResource(id = R.string.sea_creature_progress),
                        progress = seaCreaturesProgress,
                        onClick = { navigateToMonthlyCollectible(CollectibleSequence.SEA_CREATURE) }
                    )
                    Spacer(modifier = Modifier.height(Dimens.SpacingSmall))

                    // 현재 수집 가능
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
                    ) {
                        NkText(
                            text = stringResource(id = R.string.currently_collectibles),
                            style = NkTheme.typography.titleLarge,
                            modifier = Modifier.clickable {
                                Toast.makeText(
                                    context,
                                    getCollectionRateToastMessage(overallProgress, context),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                        CollectiblesRow(
                            collectibles = currentlyCollectibleBugs,
                            isHuntingMode = true,
                            isNorth = isNorth,
                            onClick = { viewModel.onClickCollectibleItem(it) },
                            onLongClick = { viewModel.onLongClickCollectibleItem(it) }
                        )
                        CollectiblesRow(
                            collectibles = currentlyCollectibleFishes,
                            isHuntingMode = true,
                            isNorth = isNorth,
                            onClick = { viewModel.onClickCollectibleItem(it) },
                            onLongClick = { viewModel.onLongClickCollectibleItem(it) }
                        )
                        CollectiblesRow(
                            collectibles = currentlyCollectibleSeaCreature,
                            isHuntingMode = true,
                            isNorth = isNorth,
                            onClick = { viewModel.onClickCollectibleItem(it) },
                            onLongClick = { viewModel.onLongClickCollectibleItem(it) }
                        )
                        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                    }

                    // 이번 달 잡지 않은 아이템
                    FadeAnimatedVisibility(visible = uncollectedForMonth.isNotEmpty()) {
                        Column(verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)) {
                            NkText(
                                text = stringResource(
                                    id = R.string.uncollected_for_month_title,
                                    uncollectedCountForMonth
                                ),
                                style = NkTheme.typography.titleLarge,
                                modifier = Modifier.clickable {
                                    Toast.makeText(
                                        context,
                                        getCollectionRateToastMessage(overallProgress, context),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                            CollectiblesRow(
                                collectibles = uncollectedForMonth,
                                isHuntingMode = false,
                                isNorth = isNorth,
                                onClick = { viewModel.onClickCollectibleItem(it) },
                                onLongClick = { viewModel.onLongClickCollectibleItem(it) }
                            )
                            Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                        }
                    }
                }
            }
        }

        // Dialogs
        DetailCollectibleDialog(
            collectible = collectibleForDetailCollectibleDialog,
            onDismiss = viewModel::onDismissDetailCollectibleDialog,
            isNorth = viewModel.getIsNorthForActiveUser(),
        )

        NkDialog(
            visible = collectibleForCollectDialog != null,
            description = stringResource(id = R.string.collect_item, collectibleForCollectDialog?.name?:""),
            onDismissRequest = viewModel::onDismissCollectDialog,
            onConfirmation = viewModel::onConfirmCollectDialog
        )

        // 박물관 가이드 보여진 적이 없을 때만 표시
        FadeAnimatedVisibility(visible = !hasMuseumGuideShown) {
            val imageUrl = if (Locale.current.language == "ko") {
                "https://firebasestorage.googleapis.com/v0/b/acnh-1be21.appspot.com/o/museum_guide_ko.jpg?alt=media&token=20973767-5df0-4feb-ae2d-fb9db7695c12"
            } else {
                "https://firebasestorage.googleapis.com/v0/b/acnh-1be21.appspot.com/o/museum_guide_en.jpg?alt=media&token=b33d71e9-da8d-4105-b7e8-74d3414d1bea"
            }
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { viewModel.setHasMuseumGuideShownTrue() },
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@Composable
fun CollectionProgress(
    name: String,
    progress: Float,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(NkTheme.colorScheme.onBackground, RoundedCornerShape(Dimens.SpacingMedium))
            .padding(Dimens.SpacingMedium),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            NkText(
                text = name,
                maxLines = 1,
            )
            NkText(text = "${(progress * 100).toInt()}%")
        }
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = { progress },
            color = NkTheme.colorScheme.tertiary,
        )
    }
}

@Composable
fun CollectiblesRow(
    collectibles: List<Collectible>,
    isHuntingMode: Boolean,
    isNorth: Boolean,
    onClick: (Collectible) -> Unit,
    onLongClick: (Collectible) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .background(
                NkTheme.colorScheme.onBackground,
                RoundedCornerShape(Dimens.SpacingMedium)
            )
            .fillMaxWidth()
            .padding(Dimens.SpacingSmall)
    ) {
        items(collectibles) {
            CollectibleItem(
                item = it, isHuntingMode = isHuntingMode,
                onClick = { onClick(it) },
                onLongClick = { onLongClick(it) },
                isNorth = isNorth
            )
        }
    }
}

private fun getCollectionRateToastMessage(overallProgress: Float, context: Context): String =
    if (.00f <= overallProgress && overallProgress < .25f) context.getString(R.string.collection_rate_0_to_25_message)
    else if (.25f <= overallProgress && overallProgress < .50f) context.getString(R.string.collection_rate_25_to_50_message)
    else if (.50f <= overallProgress && overallProgress < .75f) context.getString(R.string.collection_rate_50_to_75_message)
    else if (.75f <= overallProgress && overallProgress < 1.00f) context.getString(R.string.collection_rate_75_to_99_message)
    else if (overallProgress <= 1.00f) context.getString(R.string.collection_rate_100_message)
    else ""