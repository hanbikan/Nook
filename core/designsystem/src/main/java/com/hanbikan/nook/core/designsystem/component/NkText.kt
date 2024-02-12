package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.hanbikan.nook.core.designsystem.theme.Fonts
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun NkText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = NkTheme.colorScheme.primary,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = Fonts.joa,
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
        fontFamily = fontFamily,
        textDecoration = textDecoration,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

@Composable
fun NkText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = NkTheme.colorScheme.primary,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = Fonts.joa,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        textDecoration = textDecoration,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines,
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
        style = style,
    )
}