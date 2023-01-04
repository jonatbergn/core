package com.jonatbergn.core.iceandfire.foundation.entity

import kotlinx.collections.immutable.ImmutableList

/**
 * An entry of linked page data
 *
 * @param url the url of this page
 * @param data the containing list of data
 * @param next the link of the next page
 */
data class Page<T>(
    val url: String,
    val next: String?,
    private val data: ImmutableList<T>,
) : ImmutableList<T> by data
