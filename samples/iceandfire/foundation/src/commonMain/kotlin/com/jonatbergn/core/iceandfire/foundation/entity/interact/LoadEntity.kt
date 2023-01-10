package com.jonatbergn.core.iceandfire.foundation.entity.interact

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.interact.SupervisedInteraction

abstract class LoadEntity<T : Entity>(
    private val pointer: Pointer<T>,
    private val repo: Repo<T>,
    private val loadDependents: suspend (T) -> Unit,
) : SupervisedInteraction<T>() {

    final override suspend fun onInvoke(): T {
        val entity = repo.fetch(pointer)
        onEntityFetched()
        loadDependents(entity)
        return entity
    }

    abstract fun onEntityFetched()
}
