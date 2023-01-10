package com.jonatbergn.cars.state

import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

data class CarDetailState(
    val car: Pointer<Car>,
    val id: Long?,
    val name: String?,
    val shortDescription: String?,
    val description: String?,
    val imageUrl: String?,
)
