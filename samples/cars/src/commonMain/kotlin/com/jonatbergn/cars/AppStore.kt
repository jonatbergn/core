package com.jonatbergn.cars

import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.booking.interact.LoadBooking
import com.jonatbergn.cars.booking.interact.LoadBookings
import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.car.interact.BookCar
import com.jonatbergn.cars.car.interact.LoadCar
import com.jonatbergn.cars.car.interact.LoadCarStubs
import com.jonatbergn.cars.reservation.Reservation
import com.jonatbergn.cars.reservation.interact.EditReservation
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration.Companion.days

interface AppStore {

    val state: StateFlow<State>

    /**
     * As a user I want to see a list of all available cars, sorted alphabetically by the name.
     */
    suspend fun loadCarStubs(): Page<Car>?

    /**
     * As a user I want to see the details for each car, including the provided image, if possible.
     */
    suspend fun loadCar(car: Pointer<Car>): Car

    /**
     * As a user I want to be able to book a car in the details view.
     */
    suspend fun bookCar(car: Pointer<Car>): Pointer<Booking>

    /**
     * As a user I want to see a list of all my recent and former bookings (storage local only, preparation for backend sync should be considered).
     */
    suspend fun loadBookings(): Page<Booking>?

    /**
     * As a user I want to see the details for each booking containing car and booking information.
     */
    suspend fun loadBooking(booking: Pointer<Booking>): Booking

    /**
     * As a user I want to be able to set a custom booking start date and time (default is the next day at 9:00 am).
     * As a user I want to be able to define the booking duration per day (minimum 1 day (24 h), maximum 7 days)
     */
    suspend fun editReservation(car: Pointer<Car>, block: Reservation.() -> Reservation = { this })

    companion object Factory {

        operator fun invoke(
            context: AppContext,
        ): AppStore = with(context) {
            AppStoreImpl(
                state = state,
                createBooking = context::createBooking,
                carRepo = carRepo,
                carStubRepo = carStubRepo,
                bookingRepo = bookingRepo,
            )
        }

        suspend fun AppStore.increaseReservationDurationByOneDay(car: Pointer<Car>) = editReservation(car) {
            copy(duration = duration + 1.days)
        }

        suspend fun AppStore.decreaseReservationDurationByOneDay(car: Pointer<Car>) = editReservation(car) {
            copy(duration = duration - 1.days)
        }
    }
}

private class AppStoreImpl(
    override val state: MutableStateFlow<State>,
    private val createBooking: suspend (Reservation) -> Pointer<Booking>,
    private val carStubRepo: Repo<Car>,
    private val carRepo: Repo<Car>,
    private val bookingRepo: Repo<Booking>,
) : AppStore {

    override suspend fun loadCarStubs() = LoadCarStubs(
        state,
        carStubRepo,
    ).invoke()

    override suspend fun loadCar(car: Pointer<Car>) = LoadCar(
        state,
        car,
        carRepo,
    ).invoke()

    override suspend fun bookCar(car: Pointer<Car>) = BookCar(
        createBooking,
        state,
        bookingRepo,
        car,
    ).invoke()

    override suspend fun loadBookings() = LoadBookings(
        state,
        bookingRepo,
    ).invoke()

    override suspend fun loadBooking(booking: Pointer<Booking>) = LoadBooking(
        state,
        booking,
        bookingRepo,
        loadDependents = { loadCar(it.car) },
    ).invoke()

    override suspend fun editReservation(car: Pointer<Car>, block: Reservation.() -> Reservation) = EditReservation(
        state,
        car,
        block,
    ).invoke()
}
