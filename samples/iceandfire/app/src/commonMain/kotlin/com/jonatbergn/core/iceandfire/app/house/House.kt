package com.jonatbergn.core.iceandfire.app.house

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.foundation.entity.Dependent
import com.jonatbergn.core.iceandfire.foundation.entity.Entity

/**
 * House data for the _ice and fire api_
 *
 * @param url the resource identifier
 * @see [HouseDto]
 */
data class House(
    override val url: String,
    val name: String?,
    val region: String?,
    val coatOfArms: String?,
    val words: String?,
    val titles: List<String>?,
    val seats: List<String>?,
    val founded: String?,
    val diedOut: String?,
    val ancestralWeapons: List<String>?,
    val heir: Dependent<Character>,
    val currentLord: Dependent<Character>,
    val founder: Dependent<Character>,
    val swornMembers: List<Dependent<Character>>,
    val overlord: Dependent<House>,
    val cadetBranches: List<Dependent<House>>,
) : Entity
