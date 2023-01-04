package com.jonatbergn.core.iceandfire.app.house

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

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

    // characters
    val heir: Pointer<Character>?,
    val currentLord: Pointer<Character>?,
    val founder: Pointer<Character>?,
    val swornMembers: List<Pointer<Character>>?,

    // houses
    val overlord: Pointer<House>?,
    val cadetBranches: List<Pointer<House>>?,
) : Entity
