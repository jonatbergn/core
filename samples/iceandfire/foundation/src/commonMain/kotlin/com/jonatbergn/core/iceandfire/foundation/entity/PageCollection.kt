package com.jonatbergn.core.iceandfire.foundation.entity

import kotlinx.datetime.Instant

/**
 * A [PageCollection] represents holds pages of type [T]
 *
 * @param created the [Instant] of instance creation
 * @param start the first [Page]
 * @param more more [Page]s behind [start]
 */
data class PageCollection<T : Entity>(
    private val start: Page<T>,
    private val more: Set<Page<T>>,
) : Iterable<Page<T>> {

    override fun iterator() = iterator {
        var page = start
        while (true) {
            yield(page)
            page = more.firstOrNull { it.url == page.next } ?: break
        }
    }
}
