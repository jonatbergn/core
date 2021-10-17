package com.jonatbergn.core.iceandfire.foundation.interact

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.interact.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
abstract class ObserveEntities<T : Entity>(
    private val repo: Repo<T>,
) : UseCase<Any> {

    final override fun Flow<*>.filter() = flowOf(Any())
    final override suspend fun interactOn(action: Any) = channelFlow {
        repo.observePages().collect { onUpdate(it) }
    }

    abstract suspend fun SendChannel<Any>.onUpdate(entities: PageCollection<T>)
}
