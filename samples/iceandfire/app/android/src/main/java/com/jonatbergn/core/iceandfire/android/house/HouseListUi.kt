package com.jonatbergn.core.iceandfire.android.house

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jonatbergn.core.iceandfire.android.App.Companion.store
import com.jonatbergn.core.iceandfire.android.PageLoadingIndicator
import com.jonatbergn.core.iceandfire.android.UiState.Factory.asUiState
import com.jonatbergn.core.iceandfire.app.AppStore
import kotlinx.coroutines.launch

@Composable
private fun HouseListItem(
    state: HouseGrossUiState,
    onSelectHouseUrl: (String) -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(8.dp))
        Card { HouseGrossUi(state = state, onSelectUrl = onSelectHouseUrl) }
    }
}

@Composable
fun HouseListUi(
    onSelectHouseUrl: (String) -> Unit,
    store: AppStore = LocalContext.current.store,
) {
    val scope = rememberCoroutineScope()
    HouseListUi(
        state = store.state.collectAsState().value.asUiState.houseList,
        onLoadNext = { scope.launch { store.loadNextHousePage() } },
        onSelectHouseUrl = onSelectHouseUrl,
    )
}

/**
 * Displays [state] house date in a vertical list.
 * Scrolling the list to end will trigger [onLoadNext] if next items can be fetched.
 */
@Composable
fun HouseListUi(
    state: HouseListUiState,
    onLoadNext: () -> Unit,
    onSelectHouseUrl: (String) -> Unit,
) = with(state) {
    val totalItemsCountWhenFetched = remember { mutableStateOf(-1) }
    val listState = rememberLazyListState()
    with(listState.layoutInfo) {
        val reachedEnd = visibleItemsInfo.any { it.index == totalItemsCount - 1 }
        if (reachedEnd && totalItemsCountWhenFetched.value < totalItemsCount && isMoreHousesAvailable == true) {
            totalItemsCountWhenFetched.value = totalItemsCount
            onLoadNext()
        }
    }
    Scaffold(topBar = { TopAppBar(title = { Text("Houses of Westeros") }) }) {
        LazyColumn(
            modifier = Modifier.padding(it),
            state = listState,
        ) {
            item { Spacer(Modifier.height(16.dp)) }
            items(houses) { item ->
                HouseListItem(
                    state = item,
                    onSelectHouseUrl = onSelectHouseUrl,
                )
            }
            item {
                PageLoadingIndicator(
                    isLoadPageInFlight = loadNextHousesInFlight,
                    isMorePagesAvailable = isMoreHousesAvailable,
                )
            }
        }
    }
}
