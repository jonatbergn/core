package com.jonatbergn.core.cars.android.car

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jonatbergn.cars.AppStore
import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.state.CarGrossState
import com.jonatbergn.cars.state.CarListState
import com.jonatbergn.core.cars.android.App.Companion.store
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

@Composable
private fun CarListItem(
    state: CarGrossState,
    onClicked: () -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(8.dp))
        Card { CarGrossUi(state = state, onClicked = onClicked) }
    }
}

@Composable
private fun CarListUi(
    state: CarListState,
    onClickedCar: (Pointer<Car>) -> Unit,
) {
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
        ) {
            item { Spacer(Modifier.height(16.dp)) }
            items(state.cars) { item ->
                CarListItem(
                    state = item,
                    onClicked = { onClickedCar(item.car) },
                )
            }
        }
        if (state.isLoadingCars) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CarListUi(
    onClickedCar: (Pointer<Car>) -> Unit,
    store: AppStore = LocalContext.current.store,
) {
    CarListUi(
        state = store.state.collectAsState().value.carList(),
        onClickedCar = onClickedCar,
    )
}
