package com.jonatbergn.cars.car.interact

import app.cash.turbine.test
import com.jonatbergn.cars.FakeBookings.booking
import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.reservation.Reservation
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.repo.FakeRepo
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlin.test.Test

class BookCarTest {

    @Test
    fun testTrackGetsCalledWhenBookingGotCreated() = runTest {

        val car = Pointer<Car>("car")
        val reservation = Reservation(
            car = car,
            timeZone = TimeZone.UTC,
            now = Clock.System.now(),
        )
        val booking = booking(reservation)

        val state = MutableStateFlow(State(reservations = mapOf(car to reservation)))

        val bookCar = BookCar(
            createBooking = {
                it shouldBe reservation
                booking.pointer
            },
            state = state,
            bookingRepo = FakeRepo(
                onFetch = {
                    it shouldBe booking.pointer
                    booking
                }
            ),
            pointer = car,
        )

        state.test {
            bookCar()
            skipItems(3)
        }
    }
}
