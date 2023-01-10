package com.jonatbergn.cars.state

data class BookingListState(
    val bookings: List<BookingState>,
    val isLoadingBookings: Boolean,
)
