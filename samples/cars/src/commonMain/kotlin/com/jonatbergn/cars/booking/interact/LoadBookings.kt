package com.jonatbergn.cars.booking.interact

import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.interact.LoadNextEntityPage
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.flow.MutableStateFlow

class LoadBookings(
    private val state: MutableStateFlow<State>,
    private val repo: Repo<Booking>
) : LoadNextEntityPage<Booking>(
    repo = repo,
) {
    override fun beforeInvoke() = state { copy(loadingNextBookingPage = true) }
    override suspend fun onFetchNextPageFinished() = state { copy(bookings = repo.entities) }
    override fun afterInvoke() = state { copy(loadingNextBookingPage = false) }
}
