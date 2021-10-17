package com.jonatbergn.core.iceandfire.foundation.entity

import kotlinx.datetime.Instant

/**
 * A [PageCollection] represents holds pages of type [T]
 *
 * @param created the [Instant] of instance creation
 * @param start the first [Page]
 * @param more more [Page]s behind [start]
 */
data class PageCollection<T>(
    private val created: Instant,
    private val start: Page<T>?,
    private val more: Set<Page<T>>,
) : Iterable<Page<T>> {

    val isEmpty = start == null
    val isEnd by lazy { !isEmpty && last().next == null }

    /**
     * Converts the under lining [Page]s to a ordered list of [T]s
     */
    val itemList by lazy { toList().map(Page<T>::data).flatten() }

    private fun Page<T>.next() = more.find { it.url == next }

    /**
     * @return Returns an iterator which traverses the underlining linked paged
     */
    override fun iterator() = iterator {
        if (start != null) {
            yield(start)
            var next = start.next()
            while (next != null) {
                yield(next); next = next.next()
            }
        }
    }
}
