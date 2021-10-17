package com.jonatbergn.core.iceandfire.app

import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.states.HouseDetailsState
import com.jonatbergn.core.iceandfire.app.states.HouseListState
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection

data class State(
    val houses: PageCollection<House>? = null,
    val loadNextHousesInFlight: Boolean = false,
    val loadDetailedHouseInFlight: Boolean = false,
) {

    internal fun houseByUrl(url: String) = houses?.flatMap { it.data }
        ?.firstOrNull { it.url == url }

    companion object {
        fun State.list() = HouseListState(state = this)
        fun State.detail(url: String) = HouseDetailsState(url = url, state = this)
    }
}
