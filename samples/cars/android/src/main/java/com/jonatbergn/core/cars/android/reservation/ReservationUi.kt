package com.jonatbergn.core.cars.android.reservation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jonatbergn.cars.AppStore
import com.jonatbergn.cars.AppStore.Factory.decreaseReservationDurationByOneDay
import com.jonatbergn.cars.AppStore.Factory.increaseReservationDurationByOneDay
import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.state.ReservationState
import com.jonatbergn.core.cars.android.App.Companion.store
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ReservationUi(
    state: ReservationState,
    onNavigateBack: () -> Unit = {},
    onDecreaseDuration: () -> Unit = {},
    onIncreaseDuration: () -> Unit = {},
    onCreateBookingRequested: () -> Unit = {},
    onEditDateTimeRequested: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Reservation")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Button(
                        enabled = !state.inputBlocked,
                        onClick = onCreateBookingRequested,
                    ) {
                        Text("Order now")
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState()),
        ) {
            Box {
                Column(
                    Modifier.padding(16.dp).wrapContentHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = state.carName.orEmpty(),
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
                if (state.inputBlocked) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "${state.startDate} ${state.startTime} (${state.timeZone})",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Begin") },
                )
                Button(
                    enabled = !state.inputBlocked,
                    onClick = onEditDateTimeRequested
                ) {
                    Text("Edit")
                }
            }
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.durationInWholeDays?.toString().orEmpty(),
                    readOnly = true,
                    onValueChange = { onEditDateTimeRequested() },
                    label = { Text("Duration") },
                )
                Row {
                    Button(
                        enabled = state.canDecreaseDuration && !state.inputBlocked,
                        onClick = onDecreaseDuration,
                    ) { Text("-") }
                    Button(
                        enabled = state.canIncreaseDuration && !state.inputBlocked,
                        onClick = onIncreaseDuration,
                    ) { Text("+") }
                }
            }
        }
    }
}

@Composable
fun ReservationUi(
    car: Pointer<Car>,
    onNavigateBack: () -> Unit,
    onNavigateToBooking: (Pointer<Booking>) -> Unit,
    onEditDateTimeRequested: () -> Unit,
    store: AppStore = LocalContext.current.store,
) {
    val scope = rememberCoroutineScope()
    // edit the current reservation, but apply no changes, to fetch the current reservation
    LaunchedEffect(LocalConfiguration.current) { store.editReservation(car) }
    ReservationUi(
        state = store.state.collectAsState().value.reservation(car),
        onNavigateBack = onNavigateBack,
        onDecreaseDuration = { scope.launch { store.decreaseReservationDurationByOneDay(car) } },
        onIncreaseDuration = { scope.launch { store.increaseReservationDurationByOneDay(car) } },
        onCreateBookingRequested = { scope.launch { onNavigateToBooking(store.bookCar(car)) } },
        onEditDateTimeRequested = onEditDateTimeRequested,
    )
}
