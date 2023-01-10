package com.jonatbergn.core.iceandfire.android.house

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.jonatbergn.core.iceandfire.android.App.Companion.store
import com.jonatbergn.core.iceandfire.app.AppStore
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

@Composable
fun HouseGrossUi(
    state: HouseGrossUiState,
    onSelectUrl: (String) -> Unit = {},
    store: AppStore = LocalContext.current.store,
) {
    LaunchedEffect(Unit) { store.loadHouseGross(Pointer(state.url)) }
    Box {
        Column(
            Modifier
                .fillMaxWidth()
                .clickable { onSelectUrl(state.url) }
                .padding(horizontal = 8.dp)
        ) {
            Column(
                Modifier
                    .height(28.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    style = typography.overline,
                    text = state.lordName.orEmpty()
                )
            }
            Column(
                Modifier
                    .height(20.dp),
                verticalArrangement = Arrangement.Bottom
            ) { Text(text = state.name.orEmpty(), style = typography.subtitle2) }
            Column(
                Modifier
                    .height(40.dp),
                verticalArrangement = Arrangement.Center
            ) { Text(text = state.words.orEmpty(), style = typography.caption, fontStyle = FontStyle.Italic) }
        }
        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}
