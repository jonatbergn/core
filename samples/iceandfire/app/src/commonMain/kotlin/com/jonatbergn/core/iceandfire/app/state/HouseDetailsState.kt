package com.jonatbergn.core.iceandfire.app.state

data class HouseDetailsState(
    val url: String,
    val isLoading: Boolean,
    val name: String?,
    val lordName: String?,
    val words: String?,
    val titles: List<String>?,
    val coatOfArms: String?,
)
