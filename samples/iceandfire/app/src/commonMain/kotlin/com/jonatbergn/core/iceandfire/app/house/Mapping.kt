package com.jonatbergn.core.iceandfire.app.house

import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

/**
 * Transforms a [HouseDto] to a [House]
 */
val HouseDto.asHouse
    get() = House(
        url = url,
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        ancestralWeapons = ancestralWeapons,
        founded = founded,
        diedOut = diedOut,
        currentLord = Pointer(currentLord),
        heir = Pointer(heir),
        founder = Pointer(founder),
        swornMembers = Pointer(swornMembers),
        overlord = Pointer(overlord),
        cadetBranches = Pointer(cadetBranches),
    )

/**
 * Transorms a list of [HouseDto]s to a list of [House]s
 */
val List<HouseDto>.asHouseList
    get() = map(HouseDto::asHouse)
