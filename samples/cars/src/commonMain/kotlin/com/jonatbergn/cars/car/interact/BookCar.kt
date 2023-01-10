package com.jonatbergn.cars.car.interact

import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.reservation.Reservation
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.interact.Interaction
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.flow.MutableStateFlow

class BookCar(
    private val createBooking: suspend (Reservation) -> Pointer<Booking>,
    private val state: MutableStateFlow<State>,
    private val bookingRepo: Repo<Booking>,
    private val pointer: Pointer<Car>,
) : Interaction<Pointer<Booking>> {

    override suspend fun invoke(): Pointer<Booking> {
        val reservation = state.value.reservations[pointer]!!
        state { copy(creatingBooking = true) }
        val booking = createBooking(reservation)
        state { copy(creatingBooking = false, bookings = bookingRepo.entities) }
        return booking
    }
}
