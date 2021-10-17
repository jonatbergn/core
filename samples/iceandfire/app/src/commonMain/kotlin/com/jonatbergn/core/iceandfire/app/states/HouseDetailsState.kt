package com.jonatbergn.core.iceandfire.app.states

import com.jonatbergn.core.iceandfire.app.State

data class HouseDetailsState(
    val url: String,
    val state: State,
) {
    private val house = state.houseByUrl(url)
    val name = house?.name.orEmpty().ifBlank { "Unknown Name" }
    val lordName = house?.currentLord?.value?.name.orEmpty().ifBlank { "No current lord" }
    val words = house?.words.orEmpty().ifBlank { "This house does not have any words" }
    val titles = house?.titles.orEmpty().filterNot { it.isBlank() }
    val coatOfArms = house?.coatOfArms.orEmpty().ifBlank { "Unknown coat of arms" }
}
