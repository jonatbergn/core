package com.jonatbergn.core.iceandfire.android

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

/**
 * Wraps the [content] into [MaterialTheme] using [lightColors]
 */
@Composable
fun Theme(content: @Composable () -> Unit) {
    MaterialTheme(colors = lightColors(), content = content)
}
