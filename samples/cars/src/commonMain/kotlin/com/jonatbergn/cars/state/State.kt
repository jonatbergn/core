package com.jonatbergn.cars.state

import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.reservation.Reservation
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

data class State(
    internal val loadingCars: List<Pointer<Car>> = emptyList(),
    internal val loadingNextCarPage: Boolean = false,
    internal val loadingBookings: List<Pointer<Booking>> = emptyList(),
    internal val loadingNextBookingPage: Boolean = false,
    internal val creatingBooking: Boolean = false,
    internal val bookings: Map<Pointer<Booking>, Booking> = emptyMap(),
    internal val cars: Map<Pointer<Car>, Car> = emptyMap(),
    internal val carStubs: Map<Pointer<Car>, Car> = emptyMap(),
    internal val reservations: Map<Pointer<Car>, Reservation> = emptyMap(),
) {

    private val carStubsOrderedByName = carStubs.toList()
        .sortedBy { (_, car) -> car.name.uppercase() }
        .toMap()
        .keys

    private val bookingsOrderedByStart = bookings.toList()
        .sortedByDescending { (_, booking) -> booking.created }
        .toMap()
        .keys

    /**
     * Returns the detailed version of the car if present.
     * Otherwise, the stub version will be returned, or
     * null if none exists at all for the given pointer.
     */
    private fun Pointer<Car>?.get() = cars[this] ?: carStubs[this]

    private fun Pointer<Booking>?.get() = bookings[this]

    private fun grossCar(
        car: Pointer<Car>,
    ) = CarGrossState(
        car = car,
        id = car.get()?.id,
        name = car.get()?.name,
        shortDescription = car.get()?.shortDescription,
    )

    fun booking(
        booking: Pointer<Booking>,
    ) = BookingState(
        booking = booking,
        car = booking.get()?.car?.get(),
        start = booking.get()?.start,
        duration = booking.get()?.duration,
    )

    fun detailedCar(
        car: Pointer<Car>,
    ) = CarDetailState(
        car = car,
        id = car.get()?.id,
        name = car.get()?.name,
        shortDescription = car.get()?.shortDescription,
        description = car.get()?.description,
        imageUrl = car.get()?.imageUrl,
    )

    fun carList() = CarListState(
        cars = carStubsOrderedByName.map(::grossCar),
        isLoadingCars = loadingNextCarPage,
    )

    fun bookingList() = BookingListState(
        bookings = bookingsOrderedByStart.map(::booking),
        isLoadingBookings = loadingNextBookingPage,
    )

    fun reservation(
        car: Pointer<Car>,
    ) = ReservationState(
        car = car,
        carName = car.get()?.name,
        carId = car.get()?.id,
        timeZone = reservations[car]?.timeZone,
        startDate = reservations[car]?.startDate,
        startTime = reservations[car]?.startTime,
        start = reservations[car]?.start,
        duration = reservations[car]?.duration,
        inputBlocked = creatingBooking,
    )
}
