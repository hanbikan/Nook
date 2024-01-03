package com.hanbikan.nooknook.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nooknook.core.designsystem.R
import com.hanbikan.nooknook.core.designsystem.theme.NnTheme

data class AppBarIcon(
    val imageVector: ImageVector,
    val contentDescription: String? = null,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NnTopAppBar(
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
                            tint = NnTheme.colorScheme.primary,
                        )
                    }
                }
            }
        },
        title = {
            titleRes?.let {
                NnPrimaryText(
                    text = stringResource(id = titleRes),
                    style = NnTheme.typography.titleMedium,
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
                            tint = NnTheme.colorScheme.primary,
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = NnTheme.colorScheme.background,
        )
    )
}

@Composable
@Preview
fun NnTopAppBarPreview() {
    NnTopAppBar(
        titleRes = R.string.top_app_bar_preview_title,
        leftAppBarIcons = listOf(AppBarIcon(imageVector = Icons.Default.Home, onClick = {})),
        rightAppBarIcons = listOf(
            AppBarIcon(imageVector = Icons.Default.Search, onClick = {}),
            AppBarIcon(imageVector = Icons.Default.Person, onClick = {}),
        ),
    )
}