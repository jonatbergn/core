package com.jonatbergn.core.iceandfire.foundation.interact

import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.interact.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.supervisorScope

@ExperimentalCoroutinesApi
abstract class LoadNextPage<Action>(
    private val repo: Repo<*>,
) : UseCase<Action> {

    final override suspend fun interactOn(action: Action) = channelFlow {
        supervisorScope { onInFlight(); repo.fetchNextPage(); onComplete() }
    }

    abstract suspend fun SendChannel<Any>.onInFlight()
    abstract suspend fun SendChannel<Any>.onComplete()
}