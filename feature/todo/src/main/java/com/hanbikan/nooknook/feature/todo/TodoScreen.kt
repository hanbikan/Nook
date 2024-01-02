package com.hanbikan.nooknook.feature.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nooknook.core.designsystem.component.AppBarIcon
import com.hanbikan.nooknook.core.designsystem.component.NnTopAppBar
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
    }
}

@Preview
@Composable
fun TodoScreenPreview() {
    TodoScreen()
}