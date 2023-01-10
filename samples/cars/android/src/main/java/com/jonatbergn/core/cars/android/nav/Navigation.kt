package com.jonatbergn.core.cars.android.nav

import androidx.navigation.NavHostController
import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.cars.android.nav.ArgKeys.BookingUrl
import com.jonatbergn.core.cars.android.nav.ArgKeys.CarUrl
import com.jonatbergn.core.cars.android.nav.Destinations.Booking
import com.jonatbergn.core.cars.android.nav.Destinations.Car
import com.jonatbergn.core.cars.android.nav.Destinations.Reservation
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

object ArgKeys {
    const val CarUrl = "car_url"
    const val BookingUrl = "booking_url"
}

object Destinations {
    const val Home = "home"
    const val Car = "car?url={$CarUrl}"
    const val Reservation = "reservation?car={$CarUrl}"
    const val Booking = "booking?url={$BookingUrl}"
}

class Navigation(navController: NavHostController) {
    val car = { car: Pointer<Car> ->
        navController.navigate(route = Car.replace("{$CarUrl}", car.url))
    }
    val reservation = { car: Pointer<Car> ->
        navController.navigate(route = Reservation.replace("{$CarUrl}", car.url))
    }
    val booking = { booking: Pointer<Booking> ->
        navController.navigate(route = Booking.replace("{$BookingUrl}", booking.url))
    }
}
