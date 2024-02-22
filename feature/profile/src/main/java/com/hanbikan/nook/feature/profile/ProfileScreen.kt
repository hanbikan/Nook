package com.hanbikan.nook.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkDialogWithTextField
import com.hanbikan.nook.core.designsystem.component.NkSmallButton
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.component.TitleTextWithSpacer
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.ui.UserDialog

@Composable
fun ProfileScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value
    val isUpdateNameDialogShown =
        viewModel.isUpdateNameDialogShown.collectAsStateWithLifecycle().value
    val isUpdateIslandNameDialogShown =
        viewModel.isUpdateIslandNameDialogShown.collectAsStateWithLifecycle().value

    val activeUser = viewModel.activeUser.collectAsStateWithLifecycle().value

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
            ) {
                NkText(
                    text = stringResource(id = R.string.profile),
                    style = NkTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                )

                // 이름
                TitleTextWithSpacer(title = stringResource(id = R.string.name)) {
                    NkSmallButton(
                        onClick = viewModel::switchUpdateNameDialog,
                        imageVector = Icons.Default.Edit,
                    )
                }
                NkText(
                    text = activeUser?.name ?: "",
                    style = NkTheme.typography.headlineMedium,
                )

                // 섬 이름
                TitleTextWithSpacer(title = stringResource(id = R.string.island_name)) {
                    NkSmallButton(
                        onClick = viewModel::switchUpdateIslandNameDialog,
                        imageVector = Icons.Default.Edit,
                    )
                }
                NkText(
                    text = activeUser?.islandName ?: "",
                    style = NkTheme.typography.headlineMedium,
                )

                // TODO: 달성률
            }
        }

        UserDialog(
            visible = isUserDialogShown,
            navigateToAddUser = navigateToAddUser,
            onDismissRequest = viewModel::switchUserDialog
        )

        NkDialogWithTextField(
            visible = isUpdateNameDialogShown,
            title = stringResource(id = R.string.update_name_description),
            onDismissRequest = viewModel::switchUpdateNameDialog,
            onConfirmation = viewModel::onConfirmUpdateName,
            maxInputLength = User.NAME_MAX_LENGTH,
        )

        NkDialogWithTextField(
            visible = isUpdateIslandNameDialogShown,
            title = stringResource(id = R.string.update_island_name_description),
            onDismissRequest = viewModel::switchUpdateIslandNameDialog,
            onConfirmation = viewModel::onConfirmUpdateIslandName,
            maxInputLength = User.ISLAND_NAME_MAX_LENGTH,
        )
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(navigateToAddUser = { /*TODO*/ }, navigateToPhone = { /*TODO*/ })
}