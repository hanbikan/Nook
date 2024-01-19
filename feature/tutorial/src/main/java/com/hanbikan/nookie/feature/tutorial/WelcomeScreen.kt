package com.hanbikan.nookie.feature.tutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.hanbikan.nookie.core.designsystem.component.NnText
import com.hanbikan.nookie.core.designsystem.component.NnTextButton
import com.hanbikan.nookie.core.designsystem.theme.Dimens
import com.hanbikan.nookie.core.designsystem.theme.NnTheme

@Composable
fun WelcomeScreen(
    navigateToAddUser: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NnTheme.colorScheme.background)
            .padding(Dimens.SideMargin),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.welcome_cats),
                contentDescription = stringResource(id = R.string.welcome_message_title),
                modifier = Modifier.size(Dimens.IconExtraLarge)
            )
            NnText(
                text = stringResource(id = R.string.welcome_message_title),
                style = NnTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(Dimens.SpacingExtraSmall))
            NnText(
                text = stringResource(id = R.string.welcome_message_body),
                style = NnTheme.typography.titleMedium,
                color = NnTheme.colorScheme.primaryContainer,
            )
        }

        NnTextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = navigateToAddUser,
            text = stringResource(id = R.string.start),
        )
    }
}

@Composable
@Preview
fun WelcomeScreenPreview() {
    WelcomeScreen({})
}