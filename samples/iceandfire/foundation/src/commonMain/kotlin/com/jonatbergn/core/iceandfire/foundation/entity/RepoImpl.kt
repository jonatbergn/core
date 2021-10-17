package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.local.Local
import com.jonatbergn.core.iceandfire.foundation.remote.Remote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

/**
 *
 * @param scope the scope used to asynchronously fetch gross depending data
 * @param remote the [Remote] instance which will be used to retrieve remote entities
 * @param local the [Local] instance which will be used to retrieve local entities
 * @param fetchGross a [FetchDependents] instance for gross [Dependent] data. Gross data will be fetched,
 * whenever a new entity got fetched using [fetchNextPage]
 * @param fetchDetails a [FetchDependents] instance for detailed [Dependent] data. Detail data will be fetched,
 * whenever a single entity gets fetched using [fetchOne]
 */
class RepoImpl<T : Entity>(
    private val scope: CoroutineScope,
    private val remote: Remote<T>,
    private val local: Local<T>,
    private val fetchGross: FetchDependents<T>,
    private val fetchDetails: FetchDependents<T>,
    private val first: suspend () -> String,
) : Repo<T> {

    override fun observePages() = local.pageFlow
    override suspend fun fetchNextPage() {
        val pages = local.pageFlow.value
        val last = pages.lastOrNull()
        if (!pages.isEmpty && last?.next == null) return
        val next = remote.getMany(last?.next ?: first()).also { local.put(it) }
        scope.launch { next.data.map { fetchGross(it) }.flatten().awaitAll() }
    }

    override suspend fun fetchOne(url: String) {
        fetchDetails(local.get(url) ?: remote.getOne(url).also { local.put(it) }).awaitAll()
    }
}
