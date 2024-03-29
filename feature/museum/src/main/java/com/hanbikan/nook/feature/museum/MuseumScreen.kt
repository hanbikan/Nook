package com.hanbikan.nook.feature.museum

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.ui.UserDialog

@Composable
fun MuseumScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    viewModel: MuseumViewModel = hiltViewModel(),
) {
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

            Column(
                modifier = Modifier.padding(Dimens.SideMargin),
            ) {

            }
        }

        UserDialog(
            visible = isUserDialogShown,
            navigateToAddUser = navigateToAddUser,
            onDismissRequest = viewModel::switchUserDialog
        )
    }
}