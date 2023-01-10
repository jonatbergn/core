package com.jonatbergn.core.iceandfire.android.house

import kotlinx.collections.immutable.ImmutableList

data class HouseDetailsUiState(
    val url: String,
    val isLoading: Boolean,
    val name: String?,
    val lordName: String?,
    val words: String?,
    val titles: ImmutableList<String>?,
    val coatOfArms: String?,
)
