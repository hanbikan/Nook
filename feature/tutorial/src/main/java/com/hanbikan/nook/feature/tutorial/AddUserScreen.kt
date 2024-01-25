package com.hanbikan.nook.feature.tutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hanbikan.nook.core.designsystem.component.AppBarIcon
import com.hanbikan.nook.core.designsystem.component.NkPlaceholder
import com.hanbikan.nook.core.designsystem.component.NkText
import com.hanbikan.nook.core.designsystem.component.NkTextButton
import com.hanbikan.nook.core.designsystem.component.NkTextField
import com.hanbikan.nook.core.designsystem.component.NkTopAppBar
import com.hanbikan.nook.core.designsystem.theme.Dimens
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun AddUserScreen(
    navigateUp: () -> Unit,
    navigateToTutorial: () -> Unit,
    viewModel: AddUserViewModel = hiltViewModel(),
) {
    val name = viewModel.name.collectAsStateWithLifecycle().value
    val islandName = viewModel.islandName.collectAsStateWithLifecycle().value

    Column(modifier = Modifier.fillMaxSize()) {
        NkTopAppBar(leftAppBarIcons = listOf(AppBarIcon.backAppBarIcon(onClick = navigateUp)))
        AddUserScreenContents(
            name = name,
            islandName = islandName,
            setName = viewModel::setName,
            setIslandName = viewModel::setIslandName,
            onClickAddButton = {
                viewModel.addUser {
                    navigateToTutorial()
                }
            }
        )
    }
}

@Composable
fun AddUserScreenContents(
    name: String,
    islandName: String,
    setName: (String) -> Unit,
    setIslandName: (String) -> Unit,
    onClickAddButton: () -> Unit,
) {
    val secondFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.SideMargin),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall),
        ) {
            NkText(
                text = stringResource(id = R.string.add_user_title),
                style = NkTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            NkText(
                text = stringResource(id = R.string.add_user_body),
                color = NkTheme.colorScheme.primaryContainer
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
            NkText(
                text = stringResource(id = R.string.name),
                style = NkTheme.typography.titleMedium
            )
            NkTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = setName,
                placeholder = {
                    NkPlaceholder(text = stringResource(id = R.string.name_placeholder))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions {
                    secondFocusRequester.requestFocus()
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingExtraSmall))
            NkText(
                text = stringResource(id = R.string.island_name),
                style = NkTheme.typography.titleMedium
            )
            NkTextField(
                modifier = Modifier
                    .focusRequester(secondFocusRequester)
                    .fillMaxWidth(),
                value = islandName,
                onValueChange = setIslandName,
                placeholder = {
                    NkPlaceholder(text = stringResource(id = R.string.island_name_placeholder))
                },
                singleLine = true,
            )
        }
        NkTextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickAddButton,
            text = stringResource(id = R.string.add),
        )
    }
}

@Composable
@Preview
fun AddUserScreenPreview() {
    AddUserScreen({}, {})
}