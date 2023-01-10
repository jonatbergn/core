package com.jonatbergn.cars.booking.interact

import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.interact.LoadEntity
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.flow.MutableStateFlow

class LoadBooking(
    private val state: MutableStateFlow<State>,
    private val pointer: Pointer<Booking>,
    private val bookingRepo: Repo<Booking>,
    loadDependents: suspend (Booking) -> Unit,
) : LoadEntity<Booking>(
    pointer = pointer,
    repo = bookingRepo,
    loadDependents = loadDependents,
) {
    override fun beforeInvoke() = state { copy(loadingBookings = loadingBookings + pointer) }
    override fun onEntityFetched() = state { copy(bookings = bookingRepo.entities) }
    override fun afterInvoke() = state { copy(loadingBookings = loadingBookings - pointer) }
}
