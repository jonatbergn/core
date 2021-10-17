package com.jonatbergn.core.iceandfire.app.api

/**
 * Maps an [ApiDto] to an [Api]
 *
 * @param url the [Api.url]
 */
fun ApiDto.asApi(url: String) = Api(
    url = url,
    characters = characters,
    houses = houses,
    books = books
)
