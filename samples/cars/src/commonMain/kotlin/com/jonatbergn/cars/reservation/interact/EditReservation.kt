package com.jonatbergn.cars.reservation.interact

import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.reservation.Reservation
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.interact.Interaction
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

class EditReservation(
    private val state: MutableStateFlow<State>,
    private val car: Pointer<Car>,
    private val edit: Reservation.() -> Reservation,
    private val timeZone: () -> TimeZone = TimeZone.Companion::currentSystemDefault,
    private val now: () -> Instant = Clock.System::now,
) : Interaction<Unit> {

    override suspend fun invoke() = state {
        val reservation = reservations.getOrElse(car) { Reservation(car, timeZone(), now()) }
        val reservationEdited = reservation.edit()
        copy(reservations = reservations.plus(car to reservationEdited))
    }
}
