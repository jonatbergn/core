package com.jonatbergn.core.iceandfire.app.api

import com.jonatbergn.core.iceandfire.foundation.entity.Entity

/**
 * Entry point data for the _ice and fire api_
 *
 * @param url the base api url
 * @see [ApiDto]
 */
data class Api(
    override val url: String,
    val characters: String,
    val houses: String,
    val books: String,
) : Entity
