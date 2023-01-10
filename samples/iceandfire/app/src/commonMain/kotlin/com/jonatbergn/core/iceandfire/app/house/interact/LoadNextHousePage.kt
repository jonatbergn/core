package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.interact.LoadNextEntityPage
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.flow.MutableStateFlow

internal class LoadNextHousePage(
    private val state: MutableStateFlow<State>,
    private val houseRepo: Repo<House>,
) : LoadNextEntityPage<House>(
    repo = houseRepo,
) {

    override fun beforeInvoke() = state { copy(loadingNextHousePage = true) }

    override suspend fun onFetchNextPageFinished() = state {
        copy(
            hasMoreHousePagesToFetch = houseRepo.hasMorePagesToFetch,
            houses = houseRepo.entities,
            pagedHouses = houseRepo.pages(),
        )
    }

    override fun afterInvoke() = state { copy(loadingNextHousePage = false) }
}
