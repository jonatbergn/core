package com.jonatbergn.core.iceandfire.app.api

import kotlinx.serialization.Serializable

/**
 * Serializable response object for entry point data of the _ice and fire api_
 *
 * @see [anapioficeandfire](https://anapioficeandfire.com/Documentation#root)
 */
@Serializable
data class ApiDto(
    val characters: String,
    val houses: String,
    val books: String,
)
