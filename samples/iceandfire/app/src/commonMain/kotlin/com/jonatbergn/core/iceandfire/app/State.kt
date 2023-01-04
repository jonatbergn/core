package com.jonatbergn.core.iceandfire.app

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.states.HouseDetailsState
import com.jonatbergn.core.iceandfire.app.states.HouseGrossState
import com.jonatbergn.core.iceandfire.app.states.HouseListState
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

data class State(

    internal val houses: ImmutableMap<Pointer<House>, House> = persistentMapOf(),
    internal val characters: ImmutableMap<Pointer<Character>, Character> = persistentMapOf(),

    internal val hasMoreHousePagesToFetch: Boolean? = null,
    internal val loadingNextHousePage: Boolean = false,
    internal val loadingHouse: Boolean = false,

    internal val houseList: ImmutableList<Pointer<House>> = persistentListOf(),
    internal val characterList: ImmutableList<Pointer<Character>> = persistentListOf(),
) {

    fun Pointer<House>?.get(): House? = houses[this]
    fun Pointer<Character>?.get(): Character? = characters[this]

    private fun gross(
        house: Pointer<House>,
    ) = HouseGrossState(
        url = house.url,
        name = house.get()?.name.orEmpty(),
        words = house.get()?.words.orEmpty(),
        region = house.get()?.region.orEmpty(),
        lordName = house.get()?.currentLord?.get()?.name.orEmpty(),
        lordGender = house.get()?.currentLord?.get()?.gender.orEmpty().lowercase(),
        diedOut = house.get()?.diedOut.isNullOrBlank(),
        founded = house.get()?.founded.orEmpty().ifBlank { "unknown" },
    )

    fun detailedHouse(
        house: Pointer<House>,
    ) = HouseDetailsState(
        url = house.url,
        name = house.get()?.name.orEmpty().ifBlank { "Unknown Name" },
        lordName = house.get()?.currentLord?.get()?.name?.ifBlank { "No current lord" } ?: "",
        words = house.get()?.words.orEmpty().ifBlank { "This house does not have any words" },
        titles = house.get()?.titles.orEmpty().filterNot { it.isBlank() }.toImmutableList(),
        coatOfArms = house.get()?.coatOfArms.orEmpty().ifBlank { "Unknown coat of arms" },
    )

    fun grossHouseList() = HouseListState(
        houses = houseList.map { gross(it) }.toPersistentList(),
        isMoreHousesAvailable = hasMoreHousePagesToFetch,
        loadNextHousesInFlight = loadingNextHousePage,
    )
}
