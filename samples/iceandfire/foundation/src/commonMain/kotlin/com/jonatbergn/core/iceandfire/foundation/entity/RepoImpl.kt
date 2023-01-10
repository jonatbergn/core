package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.local.Local
import com.jonatbergn.core.iceandfire.foundation.remote.Remote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke

/**
 *
 * @param scope the scope used to asynchronously fetch gross depending data
 * @param remote the [Remote] instance which will be used to retrieve remote entities
 * @param local the [Local] instance which will be used to retrieve local entities
 * whenever a new entity got fetched using [fetchNextPage]
 * whenever a single entity gets fetched using [fetch]
 */
class RepoImpl<T : Entity>(
    private val dispatcher: CoroutineDispatcher,
    private val local: Local<T>,
    private val remote: Remote<T>,
    private val first: suspend () -> String,
) : Repo<T> {

    override val entities = local.all

    override fun pages() = local.pages()

    override val hasMorePagesToFetch
        get() = when (val pages = local.pages()) {
            null -> true
            else -> pages.last().next != null
        }

    override suspend fun fetchNextPage() = dispatcher {
        when (val pages = local.pages()) {
            null -> remote.getPage(first())
            else -> when (val next = pages.last().next) {
                null -> null
                else -> remote.getPage(next)
            }
        }?.also(local::put)
    }

    override suspend fun fetch(pointer: Pointer<T>) = dispatcher {
        local[pointer.url] ?: remote.getOne(pointer.url).also(local::put)
    }
}
