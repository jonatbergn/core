package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toPersistentList

interface Repo<T : Entity> {

    val entities: ImmutableMap<Pointer<T>, T>
    val pages: ImmutableList<Page<T>>?
    val hasMorePagesToFetch: Boolean?

    suspend fun fetchNextPage()
    suspend fun fetch(pointer: Pointer<T>): T

    companion object {
        val <T : Entity> Repo<T>.allPagedEntityPointers: ImmutableList<Pointer<T>>
            get() = pages?.flatten()?.map { Pointer<T>(it.url) }.orEmpty().toPersistentList()

        suspend infix operator fun <T : Entity> Repo<T>.plus(
            pointer: Pointer<T>?,
        ): ImmutableMap<Pointer<T>, T> {
            if (pointer != null) fetch(pointer)
            return entities
        }

        suspend infix operator fun <T : Entity> Repo<T>.plus(
            pointers: List<Pointer<T>>?,
        ): ImmutableMap<Pointer<T>, T> {
            pointers?.forEach { fetch(it) }
            return entities
        }
    }
}
