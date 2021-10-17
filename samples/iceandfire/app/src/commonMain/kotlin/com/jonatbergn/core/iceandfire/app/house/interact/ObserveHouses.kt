package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.State
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.interact.ObserveEntities
import com.jonatbergn.core.interact.reducer
import kotlinx.coroutines.channels.SendChannel

class ObserveHouses(repo: Repo<House>) : ObserveEntities<House>(repo) {

    override suspend fun SendChannel<Any>.onUpdate(entities: PageCollection<House>) =
        send(ObserveHousesEvent(entities))

    companion object {

        val reducer = reducer<State, ObserveHousesEvent> { copy(houses = it.collection) }
    }
}
