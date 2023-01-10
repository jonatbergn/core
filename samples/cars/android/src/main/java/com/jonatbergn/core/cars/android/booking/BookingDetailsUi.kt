@file:OptIn(ExperimentalMaterial3Api::class)

package com.jonatbergn.core.cars.android.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jonatbergn.cars.AppStore
import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.state.BookingState
import com.jonatbergn.core.cars.android.App.Companion.store
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun BookingDetailsUi(
    booking: Pointer<Booking>,
    onNavigateBack: () -> Unit,
    store: AppStore = LocalContext.current.store,
) {

    LaunchedEffect(Unit) { store.loadBooking(booking) }
    BookingDetailsUi(
        state = store.state.collectAsState().value.booking(booking),
        onNavigateBack = onNavigateBack,
    )
}

@Composable
internal fun BookingDetailsUi(
    state: BookingState,
    onNavigateBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ride") },
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
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = state.car?.name.orEmpty(),
                    style = MaterialTheme.typography.displaySmall,
                )
            }
            state.car?.imageUrl?.let { imageUrl ->
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    model = imageUrl,
                    contentDescription = null,
                )
            }
            state.car?.shortDescription?.let {
                Column(
                    Modifier
                        .padding(16.dp)
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        text = it,
                    )
                }
            }
            Card(Modifier.padding(16.dp)) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .heightIn(min = 112.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    val dateTime = state.start?.toLocalDateTime(TimeZone.currentSystemDefault())?.toString().orEmpty()
                    val duration = state.duration?.toString().orEmpty()
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = """
                                    ! 🎉 Congratulation 🎉!
                                 Your booking got confirmed. Your reservation 
                                 begins at $dateTime and is active for $duration.
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Column {
                    listOf(
                        state.booking.url,
                        state.car?.url
                    ).forEach { url ->
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = url.orEmpty(),
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 8.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}
