package com.hanbikan.nookie.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nookie.core.designsystem.R
import com.hanbikan.nookie.core.designsystem.theme.NkTheme

data class AppBarIcon(
    val imageVector: ImageVector,
    val contentDescription: String? = null,
    val onClick: () -> Unit
) {
    companion object {
        @Composable
        fun backAppBarIcon(onClick: () -> Unit) = AppBarIcon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            onClick = onClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NkTopAppBar(
    @StringRes titleRes: Int? = null,
    leftAppBarIcons: List<AppBarIcon> = listOf(),
    rightAppBarIcons: List<AppBarIcon> = listOf(),
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Row {
                leftAppBarIcons.forEach {
                    IconButton(onClick = it.onClick) {
                        Icon(
                            imageVector = it.imageVector,
                            contentDescription = it.contentDescription,
                            tint = NkTheme.colorScheme.primary,
                        )
                    }
                }
            }
        },
        title = {
            titleRes?.let {
                NkText(
                    text = stringResource(id = titleRes),
                    style = NkTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        actions = {
            Row {
                rightAppBarIcons.forEach {
                    IconButton(onClick = it.onClick) {
                        Icon(
                            imageVector = it.imageVector,
                            contentDescription = it.contentDescription,
                            tint = NkTheme.colorScheme.primary,
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = NkTheme.colorScheme.background,
        )
    )
}

@Composable
@Preview
fun NkTopAppBarPreview() {
    NkTopAppBar(
        titleRes = R.string.top_app_bar_preview_title,
        leftAppBarIcons = listOf(AppBarIcon(imageVector = Icons.Default.Home, onClick = {})),
        rightAppBarIcons = listOf(
            AppBarIcon(imageVector = Icons.Default.Search, onClick = {}),
            AppBarIcon(imageVector = Icons.Default.Person, onClick = {}),
        ),
    )
}