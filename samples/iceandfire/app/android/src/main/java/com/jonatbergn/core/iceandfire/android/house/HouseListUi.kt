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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jonatbergn.core.iceandfire.android.PageLoadingIndicator
import com.jonatbergn.core.iceandfire.app.State
import com.jonatbergn.core.iceandfire.app.State.Companion.list
import com.jonatbergn.core.iceandfire.app.house.interact.LoadNextHousesAction
import com.jonatbergn.core.iceandfire.app.states.HouseGrossState
import com.jonatbergn.core.iceandfire.app.states.HouseListState
import com.jonatbergn.core.model.Model

@Composable
private fun HouseListItem(
    state: HouseGrossState,
    onSelectUrl: (String) -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(8.dp))
        Card { HouseGrossUi(state = state, onSelectUrl = onSelectUrl) }
    }
}

@Composable
fun HouseListUi(
    model: Model<State>,
    onSelectHouseUrl: (String) -> Unit,
) {
    HouseListUi(
        state = model.states.collectAsState().value.list(),
        onLoadNext = { model.actions.tryEmit(LoadNextHousesAction) },
        onSelectHouseUrl = onSelectHouseUrl
    )
}


/**
 * Displays [state] house date in a vertical list.
 * Scrolling the list to end will trigger [onLoadNext] if next items can be fetched.
 */
@Composable
fun HouseListUi(
    state: HouseListState,
    onLoadNext: () -> Unit,
    onSelectHouseUrl: (String) -> Unit,
) = with(state) {
    val totalItemsCountWhenFetched = remember { mutableStateOf(-1) }
    val listState = rememberLazyListState()
    with(listState.layoutInfo) {
        val reachedEnd = visibleItemsInfo.any { it.index == totalItemsCount - 1 }
        if (reachedEnd && totalItemsCountWhenFetched.value < totalItemsCount && !isMoreHousesAvailable) {
            totalItemsCountWhenFetched.value = totalItemsCount
            onLoadNext()
        }
    }
    Scaffold(topBar = { TopAppBar(title = { Text("Houses of Westeros") }) }) {
        LazyColumn(state = listState) {
            item { Spacer(Modifier.height(16.dp)) }
            items(houses.orEmpty()) {
                HouseListItem(
                    state = it,
                    onSelectUrl = onSelectHouseUrl
                )
            }
            item {
                PageLoadingIndicator(
                    isLoadPageInFlight = loadNextHousesInFlight,
                    isMorePagesAvailable = !isMoreHousesAvailable
                )
            }
        }
    }
}
