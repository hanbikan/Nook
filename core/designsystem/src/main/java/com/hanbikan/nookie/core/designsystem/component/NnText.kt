package com.hanbikan.nookie.core.designsystem.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.hanbikan.nookie.core.designsystem.theme.Fonts
import com.hanbikan.nookie.core.designsystem.theme.NnTheme

@Composable
fun NnText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = NnTheme.colorScheme.primary,
    fontWeight: FontWeight? = null,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        fontFamily = Fonts.joa,
        textDecoration = textDecoration,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}