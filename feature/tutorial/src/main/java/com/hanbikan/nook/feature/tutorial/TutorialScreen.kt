package com.hanbikan.nook.feature.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.ui.UserDialog

@Composable
fun TutorialScreen(
    navigateToAddUser: () -> Unit,
    navigateToPhone: () -> Unit,
    viewModel: TutorialViewModel = hiltViewModel(),
) {
    val isUserDialogShown = viewModel.isUserDialogShown.collectAsStateWithLifecycle().value

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(NkTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NkTopAppBar(
                leftAppBarIcons = listOf(
                    AppBarIcon.appListAppBarIcon(onClick = navigateToPhone)
                ),
                rightAppBarIcons = listOf(
                    AppBarIcon.userDialogAppBarIcon(onClick = viewModel::switchUserDialog)
                ),
            )
            // TODO
        }

        if (isUserDialogShown) {
            UserDialog(
                navigateToAddUser = navigateToAddUser,
                onDismissRequest = viewModel::switchUserDialog
            )
        }
    }
}

@Composable
@Preview
fun TutorialScreenPreview() {
    TutorialScreen({}, {})
}