package com.jonatbergn.core.iceandfire.android.house

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.jonatbergn.core.iceandfire.android.App.Companion.store
import com.jonatbergn.core.iceandfire.android.Chip
import com.jonatbergn.core.iceandfire.android.UiState.Factory.asUiState
import com.jonatbergn.core.iceandfire.app.AppStore
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

@Composable
fun HouseDetailsUi(
    house: Pointer<House>,
    onNavigateBack: () -> Unit,
    store: AppStore = LocalContext.current.store,
) {
    LaunchedEffect(Unit) { store.loadHouseDetails(house) }
    HouseDetailsUi(
        state = store.state.collectAsState().value.asUiState.houseDetails(house),
        onNavigateBack = onNavigateBack,
    )
}

@Composable
internal fun HouseDetailsUi(
    state: HouseDetailsUiState,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.name.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {
        Box {
            Column(
                modifier = Modifier.padding(it),
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .height(56.dp),
                    verticalArrangement = Arrangement.Bottom
                ) { Text(text = state.name.orEmpty(), style = typography.h6) }
                Column(
                    Modifier
                        .padding(16.dp)
                        .height(28.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        style = typography.overline,
                        text = state.lordName.orEmpty()
                    )
                }
                Divider(Modifier.fillMaxWidth())
                Column(
                    Modifier
                        .padding(16.dp)
                        .height(40.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.words.orEmpty(),
                        style = typography.body2,
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(Modifier.height(8.dp))
                LazyRow(modifier = Modifier.height(32.dp)) {
                    item { Spacer(Modifier.width(16.dp)) }
                    items(state.titles.orEmpty()) { Row { Chip(it); Spacer(Modifier.width(4.dp)) } }
                    item { Spacer(Modifier.width(16.dp)) }
                }
                Spacer(Modifier.height(24.dp))
                Card(Modifier.padding(16.dp)) {
                    Column(
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .heightIn(min = 112.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = state.coatOfArms.orEmpty(),
                                style = typography.body2,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
