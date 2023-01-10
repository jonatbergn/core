package com.jonatbergn.cars.booking

import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import kotlinx.datetime.Instant
import kotlin.time.Duration

data class Booking(
    override val url: String,
    val created: Instant,
    val car: Pointer<Car>,
    val start: Instant,
    val duration: Duration,
) : Entity
