package com.jonatbergn.cars.state

import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlin.time.Duration

data class ReservationState(
    val car: Entity.Pointer<Car>,
    val carName: String?,
    val carId: Long?,
    val timeZone: TimeZone?,
    val startDate: LocalDate?,
    val startTime: LocalTime?,
    val start: Instant?,
    val inputBlocked: Boolean,
    private val duration: Duration?
) {
    val durationInWholeDays: Long? = duration?.inWholeDays
    val canDecreaseDuration: Boolean = (durationInWholeDays ?: Long.MIN_VALUE) > 1
    val canIncreaseDuration: Boolean = (durationInWholeDays ?: Long.MAX_VALUE) < 7
}
