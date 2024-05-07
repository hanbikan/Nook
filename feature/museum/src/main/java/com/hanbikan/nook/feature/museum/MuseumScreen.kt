package com.hanbikan.nook.feature.museum

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AnimatedLinearProgressIndicator
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.Collectible
import com.hanbikan.nook.core.domain.model.calculateProgress
import com.hanbikan.nook.core.ui.UserDialog
import kotlin.math.roundToInt

// 각각의 컬렉션에 대한 이름입니다.(ex. 물고기 수집률)
val progressNameIds = listOf(
    R.string.fish_progress
)

@Composable
fun MuseumScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    navigateToMonthlyCollectible: (Int) -> Unit,
    viewModel: MuseumViewModel = hiltViewModel(),
) {
    val allCollectibles = viewModel.allCollectibles.collectAsStateWithLifecycle().value
    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value

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

            LazyColumn(
                modifier = Modifier.padding(Dimens.SideMargin),
            ) {
                itemsIndexed(allCollectibles) { index, item ->
                    CollectionProgress(
                        name = stringResource(id = progressNameIds[index]),
                        collectibleList = item,
                        onClick = {
                            navigateToMonthlyCollectible(index)
                        }
                    )
                }
            }
        }

        UserDialog(
            visible = isUserDialogShown,
            navigateToAddUser = navigateToAddUser,
            onDismissRequest = viewModel::switchUserDialog
        )
    }
}

@Composable
fun CollectionProgress(
    name: String,
    collectibleList: List<Collectible>,
    onClick: () -> Unit,
) {
    val progress: Float = collectibleList.calculateProgress()

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
            NkText(text = "${(progress * 100).roundToInt()}%")
        }
        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
        AnimatedLinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = progress,
            color = NkTheme.colorScheme.tertiary,
        )
    }
}