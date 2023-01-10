package com.jonatbergn.cars.car.interact

import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.interact.LoadNextEntityPage
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.flow.MutableStateFlow

internal class LoadCarStubs(
    private val state: MutableStateFlow<State>,
    private val repo: Repo<Car>,
) : LoadNextEntityPage<Car>(
    repo = repo,
) {
    override fun beforeInvoke() = state { copy(loadingNextCarPage = true) }
    override suspend fun onFetchNextPageFinished() = state { copy(carStubs = repo.entities) }
    override fun afterInvoke() = state { copy(loadingNextCarPage = false) }
}
