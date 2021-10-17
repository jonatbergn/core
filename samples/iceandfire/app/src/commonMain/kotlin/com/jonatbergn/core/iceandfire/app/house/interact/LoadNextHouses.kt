package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.State
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.house.interact.LoadNextHousesEvent.Complete
import com.jonatbergn.core.iceandfire.app.house.interact.LoadNextHousesEvent.InFlight
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.interact.LoadNextPage
import com.jonatbergn.core.interact.reducer
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance

/**
 * Use case to load the next page of houses, if such page exists
 */
class LoadNextHouses(repo: Repo<House>) : LoadNextPage<LoadNextHousesAction>(repo) {

    override fun Flow<*>.filter() = filterIsInstance<LoadNextHousesAction>()
    override suspend fun SendChannel<Any>.onInFlight() = send(InFlight)
    override suspend fun SendChannel<Any>.onComplete() = send(Complete)

    companion object {

        val reducer = reducer<State, LoadNextHousesEvent> {
            when (it) {
                InFlight -> copy(loadNextHousesInFlight = true)
                Complete -> copy(loadNextHousesInFlight = false)
            }
        }
    }
}
