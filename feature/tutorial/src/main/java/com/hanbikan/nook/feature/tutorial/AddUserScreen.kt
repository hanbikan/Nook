package com.hanbikan.nook.feature.tutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.hanbikan.nook.core.designsystem.component.ChipGroup
import com.hanbikan.nook.core.designsystem.component.ChipItem
import com.hanbikan.nook.core.designsystem.component.NkChipGroup
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
    val isNorth = viewModel.isNorth.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    Column(modifier = Modifier.fillMaxSize()) {
        NkTopAppBar(leftAppBarIcons = listOf(AppBarIcon.backAppBarIcon(onClick = navigateUp)))
        Box {
            AddUserScreenContents(
                name = name,
                islandName = islandName,
                isNorth = isNorth,
                setName = viewModel::setName,
                setIslandName = viewModel::setIslandName,
                setIsNorth = viewModel::setIsNorth,
                onClickAddButton = {
                    viewModel.setIsLoading(true)
                    viewModel.addUser {
                        navigateToTutorial()
                    }
                },
                isLoading = isLoading,
            )

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun AddUserScreenContents(
    name: String,
    islandName: String,
    isNorth: Boolean,
    setName: (String) -> Unit,
    setIslandName: (String) -> Unit,
    setIsNorth: (Boolean) -> Unit,
    onClickAddButton: () -> Unit,
    isLoading: Boolean,
) {
    val secondFocusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.SideMargin),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall),
        ) {
            // title & description
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

            // 이름
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
                enabled = !isLoading,
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingExtraSmall))

            // 섬 이름
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
                enabled = !isLoading,
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingExtraSmall))

            // 반구
            NkText(
                text = stringResource(id = R.string.hemisphere),
                style = NkTheme.typography.titleMedium
            )
            NkChipGroup(
                chipGroup = ChipGroup(
                    chipItems = listOf(
                        ChipItem(stringResource(id = R.string.north_hemisphere)),
                        ChipItem(stringResource(id = R.string.south_hemisphere)),
                    ),
                    selectedIndex = if (isNorth) 0 else 1
                ),
                primaryColor = NkTheme.colorScheme.secondary,
                onClickItem = { index ->
                    if (!isLoading) {
                        setIsNorth(index == 0)
                    }
                }
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingExtraSmall))
        }
        NkTextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickAddButton,
            text = stringResource(id = R.string.add),
            enabled = !isLoading,
        )
    }
}

@Composable
@Preview
fun AddUserScreenPreview() {
    AddUserScreen({}, {})
}