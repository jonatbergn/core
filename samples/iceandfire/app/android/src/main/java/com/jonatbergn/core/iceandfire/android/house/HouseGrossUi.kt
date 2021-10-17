package com.jonatbergn.core.iceandfire.android.house

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.jonatbergn.core.iceandfire.app.states.HouseGrossState

@Composable
fun HouseGrossUi(
    state: HouseGrossState,
    onSelectUrl: (String) -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = { onSelectUrl(state.house.url) })
            .padding(horizontal = 8.dp)
    ) {
        Column(
            Modifier
                .height(28.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(style = typography.overline,
                text = state.lordName)
        }
        Column(
            Modifier
                .height(20.dp),
            verticalArrangement = Arrangement.Bottom
        ) { Text(text = state.name, style = typography.subtitle2) }
        Column(
            Modifier
                .height(40.dp),
            verticalArrangement = Arrangement.Center
        ) { Text(text = state.words, style = typography.caption, fontStyle = FontStyle.Italic) }
    }
}
