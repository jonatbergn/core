package com.jonatbergn.cars.car.interact

import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.interact.LoadEntity
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.flow.MutableStateFlow

internal class LoadCar(
    private val state: MutableStateFlow<State>,
    private val pointer: Pointer<Car>,
    private val carRepo: Repo<Car>,
) : LoadEntity<Car>(
    pointer = pointer,
    repo = carRepo,
    loadDependents = {},
) {
    override fun beforeInvoke() = state { copy(loadingCars = loadingCars + pointer) }
    override fun onEntityFetched() = state { copy(cars = carRepo.entities) }
    override fun afterInvoke() = state { copy(loadingCars = loadingCars - pointer) }
}
