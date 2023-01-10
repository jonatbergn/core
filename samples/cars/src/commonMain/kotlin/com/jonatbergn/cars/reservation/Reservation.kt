package com.jonatbergn.cars.reservation

import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

data class Reservation(
    val car: Pointer<Car>,
    val timeZone: TimeZone,
    val startDate: LocalDate,
    val startTime: LocalTime,
    val duration: Duration,
) {

    val start = startDate.atTime(startTime).toInstant(timeZone)

    constructor(
        car: Pointer<Car>,
        timeZone: TimeZone,
        now: Instant,
    ) : this(
        car = car,
        timeZone = timeZone,
        startDate = now.toLocalDateTime(timeZone).date + DatePeriod(days = 1),
        startTime = LocalTime(hour = 9, minute = 0),
        duration = 1.days,
    )
}
