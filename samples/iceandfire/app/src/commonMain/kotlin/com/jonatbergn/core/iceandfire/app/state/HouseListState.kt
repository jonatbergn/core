package com.jonatbergn.core.iceandfire.app.state

data class HouseListState(
    val houses: List<HouseGrossState>,
    val isMoreHousesAvailable: Boolean?,
    val loadNextHousesInFlight: Boolean,
)
