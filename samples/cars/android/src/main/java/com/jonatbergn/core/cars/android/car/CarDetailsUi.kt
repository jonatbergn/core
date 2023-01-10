@file:OptIn(ExperimentalMaterial3Api::class)

package com.jonatbergn.core.cars.android.car

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jonatbergn.cars.AppStore
import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.state.CarDetailState
import com.jonatbergn.core.cars.android.App.Companion.store
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

@Composable
fun CarDetailsUi(
    car: Pointer<Car>,
    onNavigateBack: () -> Unit,
    onNavigateToReservation: () -> Unit,
    store: AppStore = LocalContext.current.store,
) {
    LaunchedEffect(Unit) { store.loadCar(car) }
    CarDetailsUi(
        state = store.state.collectAsState().value.detailedCar(car),
        onNavigateBack = onNavigateBack,
        onNavigateToReservation = onNavigateToReservation
    )
}

@Composable
internal fun CarDetailsUi(
    state: CarDetailState,
    onNavigateBack: () -> Unit = {},
    onNavigateToReservation: () -> Unit = {}
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
        bottomBar = {
            BottomAppBar {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Button(
                        onClick = onNavigateToReservation,
                    ) {
                        Text("Book ride")
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
            Column(
                Modifier
                    .padding(16.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = state.name.orEmpty(),
                    style = MaterialTheme.typography.displaySmall,
                )
            }
            state.imageUrl?.let { imageUrl ->
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    model = imageUrl,
                    contentDescription = null,
                )
            }
            state.shortDescription?.let { shortDescription ->
                Column(
                    Modifier
                        .padding(16.dp)
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        text = shortDescription,
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
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.description.orEmpty(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = state.car.url,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 8.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Preview
@Composable
private fun CarDetailsUiPreview() {
    CarDetailsUi(
        CarDetailState(
            car = Pointer("http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/cars/6.json"),
            id = 6,
            name = "Skateboard",
            shortDescription = "With nice stickers on the skateboard to make it fancy",
            description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
            imageUrl = null,
        )
    )
}
