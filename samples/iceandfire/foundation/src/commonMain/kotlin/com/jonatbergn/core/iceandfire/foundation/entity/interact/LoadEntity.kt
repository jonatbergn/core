package com.jonatbergn.core.iceandfire.foundation.entity.interact

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.interaction.Interaction

class LoadEntity<T : Entity>(
    private val onDispatchStarted: suspend () -> Unit,
    private val onDispatchSucceeded: suspend () -> Unit,
    private val repo: Repo<T>,
    private val pointer: Pointer<T>,
) : Interaction {

    override suspend fun invoke() {
        onDispatchStarted()
        repo.fetch(pointer)
        onDispatchSucceeded()
    }
}
