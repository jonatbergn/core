package com.jonatbergn.core.iceandfire.app.character

import kotlinx.serialization.Serializable

/**
 * Serializable response object for character data of the _ice and fire api_
 *
 * @see [anapioficeandfire](https://anapioficeandfire.com/Documentation#characters)
 */
@Serializable
data class CharacterDto(
    val url: String,
    val name: String?,
    val gender: String?,
    val culture: String?,
    val born: String?,
    val died: String?,
    val titles: List<String>?,
    val aliases: List<String>?,
    val father: String?,
    val mother: String?,
    val spouse: String?,
    val allegiances: List<String>?,
    val books: List<String>?,
    val povBooks: List<String>?,
    val tvSeries: List<String>?,
    val playedBy: List<String>?,
)
