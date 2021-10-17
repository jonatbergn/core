package com.jonatbergn.core.iceandfire.app.house

import com.jonatbergn.core.iceandfire.foundation.entity.Dependent.Companion.asDependent
import com.jonatbergn.core.iceandfire.foundation.entity.Dependent.Companion.asDependents

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
        overlord = overlord.asDependent(),
        currentLord = currentLord.asDependent(),
        heir = heir.asDependent(),
        founder = founder.asDependent(),
        cadetBranches = cadetBranches.asDependents(),
        swornMembers = swornMembers.asDependents(),
    )

/**
 * Transorms a list of [HouseDto]s to a list of [House]s
 */
val List<HouseDto>.asHouseList
    get() = map(HouseDto::asHouse)
