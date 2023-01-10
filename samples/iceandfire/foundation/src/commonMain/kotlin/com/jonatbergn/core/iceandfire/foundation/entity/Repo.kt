package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

interface Repo<T : Entity> {

    val entities: Map<Pointer<T>, T>
    fun pages(): Iterable<Page<T>>?
    val hasMorePagesToFetch: Boolean?

    suspend fun fetchNextPage(): Page<T>?
    suspend fun fetch(pointer: Pointer<T>): T
}
