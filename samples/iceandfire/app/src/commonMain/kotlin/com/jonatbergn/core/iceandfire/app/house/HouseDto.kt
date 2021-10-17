package com.jonatbergn.core.iceandfire.app.house

import kotlinx.serialization.Serializable

/**
 * Serializable response object for house data of the _ice and fire api_
 *
 * @see [anapioficeandfire](https://anapioficeandfire.com/Documentation#houses)
 */
@Serializable
data class HouseDto(
    val url: String,
    val name: String?,
    val region: String?,
    val coatOfArms: String?,
    val words: String?,
    val titles: List<String>?,
    val seats: List<String>?,
    val currentLord: String?,
    val heir: String?,
    val overlord: String?,
    val founded: String?,
    val founder: String?,
    val diedOut: String?,
    val ancestralWeapons: List<String>?,
    val cadetBranches: List<String>?,
    val swornMembers: List<String>?,
)
