package com.jonatbergn.cars.state

data class CarListState(
    val cars: List<CarGrossState>,
    val isLoadingCars: Boolean,
)
