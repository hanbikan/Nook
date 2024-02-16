package com.hanbikan.nook.core.designsystem.component

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import com.hanbikan.nook.core.designsystem.theme.Fonts
import com.hanbikan.nook.core.designsystem.theme.NkTheme

@Composable
fun NkTextWithContentAfter(
    text: String,
    color: Color = NkTheme.colorScheme.primary,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = Fonts.joa,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    content: @Composable () -> Unit,
) {
    MeasureViewSize(
        viewToMeasure = content,
    ) {
        val id = "inlineContent"
        val annotatedString = buildAnnotatedString {
            append(text)
            appendInlineContent(id)
        }

        val inlineContent = LocalDensity.current.run {
            mapOf(
                Pair(
                    id,
                    InlineTextContent(
                        placeholder = Placeholder(
                            it.width.toSp(),
                            it.height.toSp(),
                            PlaceholderVerticalAlign.Center
                        )
                    ) {
                        content()
                    }
                )
            )
        }

        NkText(
            text = annotatedString,
            color = color,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            textAlign = textAlign,
            overflow = overflow,
            maxLines = maxLines,
            onTextLayout = onTextLayout,
            style = style,
            inlineContent = inlineContent,
        )
    }
}

@Composable
fun MeasureViewSize(
    viewToMeasure: @Composable () -> Unit,
    content: @Composable (DpSize) -> Unit,
) {
    SubcomposeLayout { constraints ->
        // subcompose로 크기 측정
        val measuredSize = subcompose("viewToMeasure", viewToMeasure).getOrNull(0)
            ?.measure(constraints)
            ?.let {
                DpSize(
                    width = it.width.toDp(),
                    height = it.height.toDp()
                )
            } ?: DpSize.Zero

        // content 그리기
        val contentPlaceable =
            subcompose("content") { content(measuredSize) }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}