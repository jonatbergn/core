package com.jonatbergn.core.iceandfire.foundation.repo

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

class FakeRepo<T : Entity>(
    val onHasMorePagesToFetch: () -> Boolean = { false },
    val onFetchNextPage: suspend () -> Page<T> = { throw NotImplementedError() },
    val onFetch: suspend (Pointer<T>) -> T = { throw NotImplementedError() },
    override var entities: PersistentMap<Pointer<T>, T> = persistentMapOf()
) : Repo<T> {

    override var pages: PersistentList<Page<T>> = persistentListOf()

    override val hasMorePagesToFetch get() = onHasMorePagesToFetch()

    override suspend fun fetchNextPage() {
        val page = onFetchNextPage()
        pages = pages.add(page)
        page.forEach { entities = entities.put(it.pointer, it) }
    }

    override suspend fun fetch(pointer: Pointer<T>): T {
        val entity = onFetch(pointer)
        entities = entities.put(pointer, entity)
        return entity
    }
}
