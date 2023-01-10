package com.jonatbergn.cars

import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.reservation.Reservation
import kotlinx.datetime.Clock

object FakeBookings {

    fun booking(reservation: Reservation) = Booking(
        url = "booking1",
        created = Clock.System.now(),
        car = reservation.car,
        start = reservation.start,
        duration = reservation.duration,
    )
}
