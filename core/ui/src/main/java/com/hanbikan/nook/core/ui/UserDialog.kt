package com.hanbikan.nook.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.NkDialog
import com.hanbikan.nook.core.designsystem.component.NkDialogBase
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme
import com.hanbikan.nook.core.domain.model.User

@Composable
fun UserDialog(
    navigateToAddUser: () -> Unit,
    onDismissRequest: () -> Unit,
    viewModel: UserDialogViewModel = hiltViewModel(),
) {
    val users = viewModel.users.collectAsStateWithLifecycle().value
    val activeUser = viewModel.activeUser.collectAsStateWithLifecycle().value
    val isDeleteUserDialogShown = viewModel.isDeleteUserDialogShown.collectAsStateWithLifecycle().value

    NkDialogBase(onDismissRequest) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onDismissRequest) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = NkTheme.colorScheme.primaryContainer,
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(Dimens.SpacingSmall, 0.dp, Dimens.SpacingSmall, Dimens.SpacingSmall)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(Dimens.SpacingMedium)
                    )
                    .padding(Dimens.SpacingSmall)
            ) {
                items(users) {
                    UserItem(
                        user = it,
                        isActive = activeUser?.id == it.id,
                        onClickUser = { user ->
                            viewModel.setActiveUserId(user.id)
                            onDismissRequest()
                        },
                        onLongClickUser = { user ->
                            viewModel.setUserIdToDelete(user.id)
                            viewModel.switchIsDeleteUserDialogShown()
                        },
                    )
                }
                item {
                    AddUserItem(
                        navigateToAddUser = navigateToAddUser,
                        onDismissRequest = onDismissRequest,
                    )
                }
            }
        }
    }

    if (isDeleteUserDialogShown) {
        NkDialog(
            description = stringResource(id = R.string.sure_to_delete_user),
            onDismissRequest = viewModel::switchIsDeleteUserDialogShown,
            onConfirmation = viewModel::onConfirmDeleteUser
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserItem(
    user: User,
    isActive: Boolean,
    onClickUser: (User) -> Unit,
    onLongClickUser: (User) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!isActive) {
                    Modifier.combinedClickable(
                        onClick = { onClickUser(user) },
                        onLongClick = { onLongClickUser(user) }
                    )
                } else {
                    Modifier
                }
            )
            .padding(Dimens.SpacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = user.name,
            tint = NkTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(Dimens.SpacingSmall))
        Column(modifier = Modifier.weight(1f)) {
            NkText(
                text = user.name,
                maxLines = 1,
            )
            NkText(
                text = user.islandName,
                color = NkTheme.colorScheme.primaryContainer,
                maxLines = 1,
            )
        }
        if (isActive) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = NkTheme.colorScheme.secondary,
            )
        }
    }
}

@Composable
fun AddUserItem(
    navigateToAddUser: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToAddUser()
                onDismissRequest()
            }
            .padding(Dimens.SpacingSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.add_user),
            tint = NkTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(Dimens.SpacingSmall))
        NkText(text = stringResource(id = R.string.add_user))
    }
}

@Composable
@Preview
fun UserDialogPreview() {
    UserDialog({}, {})
}