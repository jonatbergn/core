package com.jonatbergn.core.iceandfire.app.states

import com.jonatbergn.core.iceandfire.app.State

data class HouseListState(
    val state: State,
) {
    val houses = state.houses?.flatMap { it.data }?.map(::HouseGrossState)
    val isMoreHousesAvailable = state.houses?.isEnd != false
    val loadNextHousesInFlight = state.loadNextHousesInFlight
}
