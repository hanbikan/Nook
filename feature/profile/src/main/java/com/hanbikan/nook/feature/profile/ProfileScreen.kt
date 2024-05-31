package com.hanbikan.nook.feature.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.ChipGroup
import com.hanbikan.nook.core.designsystem.component.ChipItem
import com.hanbikan.nook.core.designsystem.component.NkChipGroup
import com.hanbikan.nook.core.designsystem.component.NkDialogWithTextField
import com.hanbikan.nook.core.designsystem.component.NkSmallButton
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.component.TitleTextWithSpacer
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.ui.UserDialog
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value
    val isUpdateNameDialogShown =
        viewModel.isUpdateNameDialogShown.collectAsStateWithLifecycle().value
    val isUpdateIslandNameDialogShown =
        viewModel.isUpdateIslandNameDialogShown.collectAsStateWithLifecycle().value

    val activeUser = viewModel.activeUser.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collectLatest {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.setToastMessage(null)
            }
        }
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

                // 반구
                TitleTextWithSpacer(title = stringResource(id = com.hanbikan.nook.core.ui.R.string.hemisphere)) {}
                NkChipGroup(
                    chipGroup = ChipGroup(
                        chipItems = listOf(
                            ChipItem(stringResource(id = com.hanbikan.nook.core.ui.R.string.north_hemisphere)),
                            ChipItem(stringResource(id = com.hanbikan.nook.core.ui.R.string.south_hemisphere)),
                        ),
                        selectedIndex = if (activeUser?.isNorth == true) 0 else 1
                    ),
                    onClickItem = { index ->
                        viewModel.setIsNorth(index == 0)
                    }
                )

                // 유저 데이터 업데이트
                Spacer(modifier = Modifier.height(Dimens.SpacingExtraLarge))
                NkText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { viewModel.onClickUpdateUser(context) }),
                    text = stringResource(id = R.string.update_user_data),
                    color = NkTheme.colorScheme.primaryContainer,
                    textAlign = TextAlign.Center,
                )
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