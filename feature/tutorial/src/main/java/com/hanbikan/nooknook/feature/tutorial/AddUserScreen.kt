package com.hanbikan.nooknook.feature.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nooknook.core.designsystem.component.AppBarIcon
import com.hanbikan.nooknook.core.designsystem.component.NnText
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
        Column(
            modifier = Modifier.padding(Dimens.SideMargin)
        ) {
            NnText(
                text = stringResource(id = R.string.add_user_title),
                style = NnTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingSmall))
            NnText(
                text = stringResource(id = R.string.add_user_body),
                color = NnTheme.colorScheme.primaryContainer
            )
        }
    }
}

@Composable
@Preview
fun AddUserScreenPreview() {
    AddUserScreen({})
}