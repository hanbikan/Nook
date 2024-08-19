package com.hanbikan.nook.feature.museum

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AnimatedLinearProgressIndicator
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.common.calculateProgress
import com.hanbikan.nook.core.ui.UserDialog
import com.hanbikan.nook.feature.museum.model.CollectibleSequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


private const val ANIMATION_DURATION_MILLIS: Int = 750
private const val ANIMATION_DELAY_MILLIS: Long = 0L

@Composable
fun MuseumScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    navigateToMonthlyCollectible: (Int) -> Unit,
    viewModel: MuseumViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val bugs = viewModel.bugs.collectAsStateWithLifecycle().value
    val fishes = viewModel.fishes.collectAsStateWithLifecycle().value
    val seaCreatures = viewModel.seaCreatures.collectAsStateWithLifecycle().value
    val collectiblesForMonth = viewModel.collectiblesForMonth.collectAsStateWithLifecycle().value
    val uncollectedForMonth = viewModel.notCollectedForMonth.collectAsStateWithLifecycle().value

    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value
    val collectibleToShowInDialog =
        viewModel.collectibleToShowInDialog.collectAsStateWithLifecycle().value

    var bugProgress by remember { mutableFloatStateOf(0.0f) }
    var fishProgress by remember { mutableFloatStateOf(0.0f) }
    var seaCreaturesProgress by remember { mutableFloatStateOf(0.0f) }

    var overallProgress by remember { mutableFloatStateOf(0.0f) }
    var overallProgressToShow by remember { mutableFloatStateOf(0f) }
    val animatedOverallProgress by animateFloatAsState(
        targetValue = overallProgressToShow,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS),
        label = "OverallProgress"
    )

    var uncollectedCountForMonth by remember { mutableIntStateOf(0) }
    var uncollectedCountForMonthToShow by remember { mutableIntStateOf(0) }
    val animatedUncollectedCountForMonth by animateIntAsState(
        targetValue = uncollectedCountForMonthToShow,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS),
        label = "MonthProgress"
    )

    LaunchedEffect(bugs, fishes, seaCreatures) {
        if (bugs.isNotEmpty() && fishes.isNotEmpty() && seaCreatures.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                bugProgress = bugs.calculateProgress()
                fishProgress = fishes.calculateProgress()
                seaCreaturesProgress = seaCreatures.calculateProgress()
                overallProgress = (bugProgress + fishProgress + seaCreaturesProgress) / 3.0f
            }
        }
    }

    LaunchedEffect(overallProgress) {
        delay(ANIMATION_DELAY_MILLIS)
        overallProgressToShow = overallProgress
    }

    LaunchedEffect(collectiblesForMonth) {
        if (collectiblesForMonth.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                uncollectedCountForMonth = uncollectedForMonth.count()
            }
        }
    }

    LaunchedEffect(uncollectedCountForMonth) {
        delay(ANIMATION_DELAY_MILLIS)
        uncollectedCountForMonthToShow = uncollectedCountForMonth
    }

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.appListAppBarIcon(onClick = navigateToPhone)
                ),
                rightAppBarIcons = listOf(
                    AppBarIcon.userDialogAppBarIcon(onClick = viewModel::switchUserDialog)
                ),
            )

            Column(
                modifier = Modifier.padding(Dimens.SideMargin),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
            ) {
                // 전체 수집률
                NkText(
                    text = stringResource(id = R.string.overall_progress_title, (animatedOverallProgress * 100).toInt()),
                    style = NkTheme.typography.titleLarge,
                    modifier = Modifier.clickable { Toast.makeText(context, getCollectionRateToastMessage(overallProgress, context), Toast.LENGTH_SHORT).show() }
                )
                CollectionProgress(
                    name = stringResource(id = R.string.bug_progress),
                    progress = bugProgress,
                    onClick = { navigateToMonthlyCollectible(CollectibleSequence.BUG.ordinal) }
                )
                CollectionProgress(
                    name = stringResource(id = R.string.fish_progress),
                    progress = fishProgress,
                    onClick = { navigateToMonthlyCollectible(CollectibleSequence.FISH.ordinal) }
                )
                CollectionProgress(
                    name = stringResource(id = R.string.sea_creature_progress),
                    progress = seaCreaturesProgress,
                    onClick = { navigateToMonthlyCollectible(CollectibleSequence.SEA_CREATURE.ordinal) }
                )
                Spacer(modifier = Modifier.height(Dimens.SpacingMedium))

                // 이번 달 잡지 않은 아이템
                NkText(
                    text = stringResource(id = R.string.uncollected_for_month_title, animatedUncollectedCountForMonth),
                    style = NkTheme.typography.titleLarge,
                    modifier = Modifier.clickable { Toast.makeText(context, getCollectionRateToastMessage(overallProgress, context), Toast.LENGTH_SHORT).show() }
                )
                LazyRow(
                    modifier = Modifier
                        .background(
                            NkTheme.colorScheme.onBackground,
                            RoundedCornerShape(Dimens.SpacingMedium)
                        )
                        .padding(Dimens.SpacingSmall)
                ) {
                    items(uncollectedForMonth) {
                        CollectibleItem(
                            item = it, isHuntingMode = false,
                            onClick = {},
                            onLongClick = { viewModel.onLongClickCollectibleItem(it) }
                        )
                    }
                }
            }
        }

        UserDialog(
            visible = isUserDialogShown,
            navigateToAddUser = navigateToAddUser,
            onDismissRequest = viewModel::switchUserDialog
        )

        DetailCollectibleDialog(
            collectible = collectibleToShowInDialog,
            onDismiss = viewModel::onDismissCollectibleDialog,
            isNorth = viewModel.getIsNorthForActiveUser(),
        )
    }
}

@Composable
fun CollectionProgress(
    name: String,
    progress: Float,
    onClick: () -> Unit,
) {
    var progressToShow by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progressToShow,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MILLIS),
        label = "CollectionProgress"
    )
    val progressAsPercent = "${(animatedProgress * 100).toInt()}%"

    LaunchedEffect(progress) {
        delay(ANIMATION_DELAY_MILLIS)
        progressToShow = progress
    }

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
            NkText(text = progressAsPercent)
        }
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
        AnimatedLinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = animatedProgress,
            color = NkTheme.colorScheme.tertiary,
        )
    }
}

private fun getCollectionRateToastMessage(overallProgress: Float, context: Context): String =
    if (.00f <= overallProgress && overallProgress < .25f) context.getString(R.string.collection_rate_0_to_25_message)
    else if (.25f <= overallProgress && overallProgress < .50f) context.getString(R.string.collection_rate_25_to_50_message)
    else if (.50f <= overallProgress && overallProgress < .75f) context.getString(R.string.collection_rate_50_to_75_message)
    else if (.75f <= overallProgress && overallProgress < 1.00f) context.getString(R.string.collection_rate_75_to_99_message)
    else if (overallProgress <= 1.00f) context.getString(R.string.collection_rate_100_message)
    else ""