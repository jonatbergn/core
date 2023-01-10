package com.jonatbergn.core.iceandfire.android.house

import kotlinx.collections.immutable.ImmutableList

data class HouseListUiState(
    val houses: ImmutableList<HouseGrossUiState>,
    val isMoreHousesAvailable: Boolean?,
    val loadNextHousesInFlight: Boolean,
)
