package com.jonatbergn.core.cars.android

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.cars.android.booking.BookingDetailsUi
import com.jonatbergn.core.cars.android.booking.BookingListUi
import com.jonatbergn.core.cars.android.car.CarDetailsUi
import com.jonatbergn.core.cars.android.car.CarListUi
import com.jonatbergn.core.cars.android.nav.ArgKeys
import com.jonatbergn.core.cars.android.nav.Destinations
import com.jonatbergn.core.cars.android.nav.Navigation
import com.jonatbergn.core.cars.android.reservation.ReservationUi
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

@Composable
fun Ui(
    onEditReservationDateTimeRequested: (Pointer<Car>) -> Unit,
    navController: NavHostController = rememberNavController(),
    navigation: Navigation = remember { Navigation(navController) },
) {
    NavHost(navController = navController, startDestination = Destinations.Home) {
        composable(
            Destinations.Home
        ) {
            var selectedTab by rememberSaveable { mutableStateOf(0) }
            Column {
                TabRow(
                    selectedTabIndex = selectedTab,
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Bookings") },
                        icon = {},
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("Rides") },
                        icon = {},
                    )
                }
                when (selectedTab) {
                    0 -> BookingListUi(onClickedBooking = { navigation.booking(it) })
                    1 -> CarListUi(onClickedCar = { navigation.car(it) })
                }
            }
        }
        composable(
            Destinations.Car,
            listOf(navArgument(ArgKeys.CarUrl) { type = NavType.StringType })
        ) {
            val car = Pointer<Car>(it.arguments?.getString(ArgKeys.CarUrl)!!)
            CarDetailsUi(
                car = car,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToReservation = { navigation.reservation(car) },
            )
        }
        composable(
            Destinations.Reservation,
            listOf(navArgument(ArgKeys.CarUrl) { type = NavType.StringType })
        ) {
            val car = Pointer<Car>(it.arguments?.getString(ArgKeys.CarUrl)!!)
            ReservationUi(
                car = car,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToBooking = { booking ->
                    // this is no good, as we rely on a specific stack state,
                    // yet it should be sufficient for now.
                    navController.popBackStack()
                    navController.popBackStack()
                    navigation.booking(booking)
                },
                onEditDateTimeRequested = { onEditReservationDateTimeRequested(car) },
            )
        }
        composable(
            Destinations.Booking,
            listOf(navArgument(ArgKeys.BookingUrl) { type = NavType.StringType })
        ) {
            val booking = Pointer<Booking>(it.arguments?.getString(ArgKeys.BookingUrl)!!)
            BookingDetailsUi(
                booking = booking,
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
