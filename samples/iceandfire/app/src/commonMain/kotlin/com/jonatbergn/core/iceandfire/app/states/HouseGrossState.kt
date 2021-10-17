package com.jonatbergn.core.iceandfire.app.states

import com.jonatbergn.core.iceandfire.app.house.House

data class HouseGrossState(
    val house: House,
) {
    val name = house.name.orEmpty()
    val words = house.words.orEmpty()
    val region = house.region.orEmpty()
    val lordName = house.currentLord.value?.name.orEmpty().ifBlank { "Unknown" }
    val lordGender = house.currentLord.value?.gender.orEmpty().lowercase()
    val diedOut = !house.diedOut.isNullOrBlank()
    val founded = house.founded.orEmpty().ifBlank { "unknown" }
}
