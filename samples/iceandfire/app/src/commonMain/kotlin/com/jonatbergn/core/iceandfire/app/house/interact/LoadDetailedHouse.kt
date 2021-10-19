package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.State
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.house.interact.LoadDetailedHouseEvent.Complete
import com.jonatbergn.core.iceandfire.app.house.interact.LoadDetailedHouseEvent.InFlight
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.interact.LoadDetailedEntity
import com.jonatbergn.core.interact.reducer
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

class LoadDetailedHouse(
    retry: suspend (Throwable, Long) -> Boolean,
    repo: Repo<House>,
) : LoadDetailedEntity(
    retry,
    repo,
) {

    override fun Flow<*>.filter() = filterIsInstance<LoadDetailedHouseAction>()
    override suspend fun SendChannel<Any>.onInFlight() = send(InFlight)
    override suspend fun SendChannel<Any>.onComplete() = send(Complete)

    companion object {

        val reducer = reducer<State, LoadDetailedHouseEvent> {
            when (it) {
                InFlight -> copy(loadDetailedHouseInFlight = true)
                Complete -> copy(loadDetailedHouseInFlight = false)
            }
        }
    }
}
