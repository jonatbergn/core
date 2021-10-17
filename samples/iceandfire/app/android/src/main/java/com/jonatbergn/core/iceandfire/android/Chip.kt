package com.jonatbergn.core.iceandfire.android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun Chip(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onSelect: () -> Unit,
    content: @Composable () -> Unit,
) {
    Row(
        modifier
            .height(32.dp)
            .border(
                width = 1.dp,
                color = if (selected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                },
                shape = CircleShape
            )
            .background(
                color = if (selected) {
                    MaterialTheme.colors.primary.copy(alpha = 0.12f)
                } else {
                    MaterialTheme.colors.surface
                },
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable(onClick = onSelect)
            .padding(horizontal = 12.dp)
    ) { content() }
}

/**
 * A material [Chip](https://material.io/components/chips) implementation showing a text.
 */
@Composable
fun Chip(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Chip(
        selected = selected,
        onSelect = onClick,
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                maxLines = 1,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f)
            )
        }
    }
}

@Preview
@Composable
fun ChipPreview() {
    Chip(text = "Foo")
}
