package com.jonatbergn.core.iceandfire.foundation.entity.interact

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.interact.SupervisedInteraction

/**
 * Use case to load the next page of houses, if such page exists
 */
abstract class LoadNextEntityPage<T : Entity>(
    private val repo: Repo<T>,
) : SupervisedInteraction<Page<T>?>() {

    override suspend fun onInvoke(): Page<T>? {
        val page = repo.fetchNextPage()
        onFetchNextPageFinished()
        return page
    }

    abstract suspend fun onFetchNextPageFinished()
}
