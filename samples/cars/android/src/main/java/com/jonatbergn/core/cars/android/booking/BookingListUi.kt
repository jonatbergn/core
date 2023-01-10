package com.jonatbergn.core.cars.android.booking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jonatbergn.cars.AppStore
import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.state.BookingListState
import com.jonatbergn.cars.state.BookingState
import com.jonatbergn.core.cars.android.App.Companion.store
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer


@Composable
private fun BookingListItem(
    state: BookingState,
    onClicked: () -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(8.dp))
        Card { BookingGrossUi(state = state, onClicked = onClicked) }
    }
}

@Composable
private fun BookingListUi(
    state: BookingListState,
    onClickedBooking: (Pointer<Booking>) -> Unit,
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
        ) {
            item { Spacer(Modifier.height(16.dp)) }
            items(state.bookings) { item ->
                BookingListItem(
                    state = item,
                    onClicked = { onClickedBooking(item.booking) },
                )
            }
        }
        if (state.isLoadingBookings) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BookingListUi(
    onClickedBooking: (Pointer<Booking>) -> Unit,
    store: AppStore = LocalContext.current.store,
) {
    BookingListUi(
        state = store.state.collectAsState().value.bookingList(),
        onClickedBooking = onClickedBooking,
    )
}
