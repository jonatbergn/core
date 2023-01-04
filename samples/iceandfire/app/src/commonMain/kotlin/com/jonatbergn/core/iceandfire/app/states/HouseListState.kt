package com.jonatbergn.core.iceandfire.app.states

import kotlinx.collections.immutable.ImmutableList

data class HouseListState(
    val houses: ImmutableList<HouseGrossState>,
    val isMoreHousesAvailable: Boolean?,
    val loadNextHousesInFlight: Boolean,
)
