package com.hanbikan.nook.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nook.core.designsystem.R
import com.hanbikan.nook.core.designsystem.theme.NkTheme

data class AppBarIcon(
    val imageVector: ImageVector,
    val contentDescription: String? = null,
    val onClick: () -> Unit,
    val DropDownMenu: @Composable () -> Unit = {},
) {
    companion object {
        @Composable
        fun backAppBarIcon(onClick: () -> Unit) = AppBarIcon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            onClick = onClick
        )

        @Composable
        fun appListAppBarIcon(onClick: () -> Unit) = AppBarIcon(
            imageVector = Icons.Default.Home,
            contentDescription = stringResource(id = R.string.app_list),
            onClick = onClick
        )

        @Composable
        fun userDialogAppBarIcon(onClick: () -> Unit) = AppBarIcon(
            imageVector = Icons.Default.Person,
            contentDescription = stringResource(id = R.string.user_dialog),
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
            leftAppBarIcons.forEach {
                IconButton(onClick = it.onClick) {
                    Icon(
                        imageVector = it.imageVector,
                        contentDescription = it.contentDescription,
                        tint = NkTheme.colorScheme.primary,
                    )
                }

                it.DropDownMenu()
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
            rightAppBarIcons.forEach {
                var buttonOffset by remember { mutableStateOf(Offset.Zero) }

                IconButton(
                    onClick = it.onClick,
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        buttonOffset = coordinates.localToWindow(Offset.Zero)
                    }
                ) {
                    Icon(
                        imageVector = it.imageVector,
                        contentDescription = it.contentDescription,
                        tint = NkTheme.colorScheme.primary,
                    )
                }

                it.DropDownMenu()
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
        )
    )
}