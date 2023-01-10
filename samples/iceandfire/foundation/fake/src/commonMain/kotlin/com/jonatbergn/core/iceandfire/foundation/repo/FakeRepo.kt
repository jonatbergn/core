package com.jonatbergn.core.iceandfire.foundation.repo

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.Repo

class FakeRepo<T : Entity>(
    val onHasMorePagesToFetch: () -> Boolean = { false },
    val onFetchNextPage: suspend () -> Page<T>? = { throw NotImplementedError() },
    val onFetch: suspend (Pointer<T>) -> T = { throw NotImplementedError() },
    override val entities: MutableMap<Pointer<T>, T> = mutableMapOf()
) : Repo<T> {

    private val pagesCache = mutableListOf<Page<T>>()

    override fun pages() = pagesCache

    override val hasMorePagesToFetch get() = onHasMorePagesToFetch()

    override suspend fun fetchNextPage(): Page<T>? {
        val page = onFetchNextPage() ?: return null
        pagesCache.add(page)
        entities.putAll(page.associateBy { it.pointer })
        return page
    }

    override suspend fun fetch(pointer: Pointer<T>): T {
        val entity = onFetch(pointer)
        entities[pointer] = entity
        return entity
    }
}
