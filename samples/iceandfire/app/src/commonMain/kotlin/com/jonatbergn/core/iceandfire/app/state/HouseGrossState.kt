package com.jonatbergn.core.iceandfire.app.state

data class HouseGrossState(
    val url: String,
    val isLoading: Boolean,
    val name: String?,
    val words: String?,
    val region: String?,
    val lordName: String?,
    val lordGender: String?,
    val diedOut: Boolean?,
    val founded: String?,
)
