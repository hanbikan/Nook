package com.hanbikan.nooknook.feature.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanbikan.nooknook.core.designsystem.component.AppBarIcon
import com.hanbikan.nooknook.core.designsystem.component.NnPlaceholder
import com.hanbikan.nooknook.core.designsystem.component.NnText
import com.hanbikan.nooknook.core.designsystem.component.NnTextButton
import com.hanbikan.nooknook.core.designsystem.component.NnTextField
import com.hanbikan.nooknook.core.designsystem.component.NnTopAppBar
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@Composable
fun AddUserScreen(
    navigateUp: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NnTheme.colorScheme.background),
    ) {
        NnTopAppBar(leftAppBarIcons = listOf(AppBarIcon.backAppBarIcon(onClick = navigateUp)))
        AddUserScreenContents()
    }
}

@Composable
fun AddUserScreenContents() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.SideMargin),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingSmall),
        ) {
            NnText(
                text = stringResource(id = R.string.add_user_title),
                style = NnTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            NnText(
                text = stringResource(id = R.string.add_user_body),
                color = NnTheme.colorScheme.primaryContainer
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingLarge))
            NnText(
                text = stringResource(id = R.string.name),
                style = NnTheme.typography.titleMedium
            )
            NnTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = {
                    NnPlaceholder(text = stringResource(id = R.string.name_placeholder))
                }
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingExtraSmall))
            NnText(
                text = stringResource(id = R.string.island_name),
                style = NnTheme.typography.titleMedium
            )
            NnTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                placeholder = {
                    NnPlaceholder(text = stringResource(id = R.string.island_name_placeholder))
                }
            )
        }
        NnTextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /* TODO */ },
            text = stringResource(id = R.string.add),
        )
    }
}

@Composable
@Preview
fun AddUserScreenPreview() {
    AddUserScreen({})
}