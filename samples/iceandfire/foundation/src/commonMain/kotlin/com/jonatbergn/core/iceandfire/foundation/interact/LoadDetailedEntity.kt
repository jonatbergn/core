package com.jonatbergn.core.iceandfire.foundation.interact

import com.jonatbergn.core.exception.handleExceptions
import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.interact.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
abstract class LoadDetailedEntity(
    private val retry: suspend (Throwable, Long) -> Boolean,
    private val repo: Repo<out Entity>,
) : UseCase<LoadDetailedEntityAction> {

    final override suspend fun interactOn(action: LoadDetailedEntityAction) = channelFlow {
        onInFlight(); repo.fetchOne(action.url); onComplete()
    }.handleExceptions { retryWhen(retry) }

    abstract suspend fun SendChannel<Any>.onInFlight()
    abstract suspend fun SendChannel<Any>.onComplete()
}
