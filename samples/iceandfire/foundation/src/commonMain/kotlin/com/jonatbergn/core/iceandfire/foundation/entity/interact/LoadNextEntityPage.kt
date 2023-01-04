package com.jonatbergn.core.iceandfire.foundation.entity.interact

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.interaction.Interaction
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * Use case to load the next page of houses, if such page exists
 */
abstract class LoadNextEntityPage<T : Entity>(
    private val repo: Repo<T>,
) : Interaction {

    override suspend fun invoke(): Unit = with(repo) {
        if (hasMorePagesToFetch == false) onFetchNextPageSucceeded()
        else coroutineScope {
            onFetchNextPageStarted()
            fetchNextPage()
            onFetchNextPageSucceeded()
            pages
                ?.lastOrNull()
                ?.asSequence()
                ?.map { async { fetchDependents(it.pointer) } }
                ?.toList()
                ?.awaitAll()
        }
    }

    abstract suspend fun onFetchNextPageStarted()
    abstract suspend fun onFetchNextPageSucceeded()
    abstract suspend fun fetchDependents(pointer: Pointer<T>)
}
