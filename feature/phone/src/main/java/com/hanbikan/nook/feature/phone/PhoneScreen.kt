package com.hanbikan.nook.feature.phone

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.ui.NkApp
import com.hanbikan.nook.core.ui.NkAppWithNavigation
import com.hanbikan.nook.core.ui.UserDialog

@Composable
fun PhoneScreen(
    nkApps: List<NkAppWithNavigation>,
    navigateToAddUser: () -> Unit,
    viewModel: PhoneViewModel = hiltViewModel(),
) {
    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NkTopAppBar(
                rightAppBarIcons = listOf(
                    AppBarIcon.userDialogAppBarIcon(onClick = viewModel::switchUserDialog),
                ),
            )
            NkText(
                text = stringResource(id = R.string.phone_screen_title),
                style = NkTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingMedium))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SideMargin, 0.dp)
            ) {
                items(nkApps) {
                    Column(
                        modifier = Modifier
                            .padding(Dimens.SpacingSmall),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = it.painter,
                            contentDescription = it.name,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(Dimens.SpacingExtraLarge))
                                .clickable(
                                    onClick = {
                                        viewModel.setLastVisitedRoute(it.route)
                                        it.navigate()
                                    },
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(
                                        bounded = true,
                                        color = NkTheme.colorScheme.primary,
                                    )
                                )
                        )
                        Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
                        NkText(text = it.name)
                    }
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
@Preview
fun PhoneScreenPreview() {
    PhoneScreen(
        listOf(
            NkApp.PROFILE.toNkAppWithNavigation("") {},
            NkApp.TUTORIAL.toNkAppWithNavigation("") {},
            NkApp.TODO.toNkAppWithNavigation("") {},
        ),
        {}
    )
}