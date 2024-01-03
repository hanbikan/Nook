package com.hanbikan.nooknook.feature.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nooknook.core.designsystem.component.AppBarIcon
import com.hanbikan.nooknook.core.designsystem.component.NnPrimaryText
import com.hanbikan.nooknook.core.designsystem.component.NnTopAppBar
import com.hanbikan.nooknook.core.designsystem.theme.Dimens
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

@Composable
fun TodoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NnTheme.colorScheme.background),
    ) {
        NnTopAppBar(
            leftAppBarIcons = listOf(
                AppBarIcon(imageVector = Icons.Default.Home, onClick = {})
            ),
            rightAppBarIcons = listOf(
                AppBarIcon(imageVector = Icons.Default.Person, onClick = {}),
            ),
        )
        TodoContents()
    }
}

@Composable
fun TodoContents() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.SideMargin)
    ) {
        NnPrimaryText(
            text = stringResource(id = R.string.welcome_text, "한빛"),
            style = NnTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun TodoScreenPreview() {
    TodoScreen()
}