package com.jonatbergn.core.iceandfire.app.character

import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.foundation.entity.Dependent
import com.jonatbergn.core.iceandfire.foundation.entity.Entity

/**
 * Character data for the _ice and fire api_
 *
 * @param url the resource identifier
 * @see [CharacterDto]
 */
data class Character(
    override val url: String,
    val name: String?,
    val gender: String?,
    val culture: String?,
    val born: String?,
    val died: String?,
    val titles: List<String>?,
    val aliases: List<String>?,
    val books: List<String>?,
    val povBooks: List<String>?,
    val tvSeries: List<String>?,
    val playedBy: List<String>?,
    val father: Dependent<Character>,
    val mother: Dependent<Character>,
    val spouse: Dependent<Character>,
    val allegiances: List<Dependent<House>>,
) : Entity
