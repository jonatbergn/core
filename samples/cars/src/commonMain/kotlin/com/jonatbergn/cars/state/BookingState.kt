package com.jonatbergn.cars.state

import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import kotlinx.datetime.Instant
import kotlin.time.Duration

data class BookingState(
    val booking: Pointer<Booking>,
    val car: Car?,
    val start: Instant?,
    val duration: Duration?,
) {
    val end: Instant? = if (start == null || duration == null) null else start + duration
}
