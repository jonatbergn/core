package com.jonatbergn.core.iceandfire.app.state

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Page

data class State(
    internal val houses: Map<Pointer<House>, House> = emptyMap(),
    internal val characters: Map<Pointer<Character>, Character> = emptyMap(),

    internal val hasMoreHousePagesToFetch: Boolean? = null,
    internal val loadingNextHousePage: Boolean = false,
    internal val loadingHouses: List<Pointer<House>> = emptyList(),

    internal val loadingCharacters: List<Pointer<Character>> = emptyList(),

    internal val pagedHouses: Iterable<Page<House>>? = null,
    internal val pagedCharacters: Iterable<Page<Character>>? = null,
) {

    fun Pointer<House>?.get(): House? = houses[this]
    fun Pointer<Character>?.get(): Character? = characters[this]

    private fun houseGross(
        house: Pointer<House>,
    ) = HouseGrossState(
        url = house.url,
        isLoading = house in loadingHouses,
        name = house.get()?.name,
        words = house.get()?.words,
        region = house.get()?.region,
        lordName = house.get()?.currentLord?.get()?.name,
        lordGender = house.get()?.currentLord?.get()?.gender,
        diedOut = house.get()?.diedOut?.isBlank(),
        founded = house.get()?.founded,
    )

    fun houseDetails(
        house: Pointer<House>,
    ) = HouseDetailsState(
        url = house.url,
        isLoading = house in loadingHouses,
        name = house.get()?.name,
        lordName = house.get()?.currentLord?.get()?.name,
        words = house.get()?.words,
        titles = house.get()?.titles,
        coatOfArms = house.get()?.coatOfArms,
    )

    val houseList by lazy {
        HouseListState(
            houses = pagedHouses?.flatten()?.map { houseGross(it.pointer) }.orEmpty(),
            isMoreHousesAvailable = hasMoreHousePagesToFetch,
            loadNextHousesInFlight = loadingNextHousePage,
        )
    }
}
