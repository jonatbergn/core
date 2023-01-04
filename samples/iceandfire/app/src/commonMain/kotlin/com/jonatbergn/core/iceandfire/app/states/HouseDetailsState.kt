package com.jonatbergn.core.iceandfire.app.states

import kotlinx.collections.immutable.ImmutableList

data class HouseDetailsState(
    val url: String,
    val name: String,
    val lordName: String,
    val words: String,
    val titles: ImmutableList<String>,
    val coatOfArms: String,
)
