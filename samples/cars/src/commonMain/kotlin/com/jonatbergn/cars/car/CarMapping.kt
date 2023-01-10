package com.jonatbergn.cars.car

import kotlin.jvm.JvmName

internal class CarMapping(
    private val carApiUrl: String,
) {

    val CarDto.asCar: Car
        get() = Car(
            url = "$carApiUrl/cars/$id.json",
            id = id,
            name = name,
            shortDescription = shortDescription,
            description = description,
            imageUrl = if (image != null) "$carApiUrl/$image" else null,
        )

    val List<CarDto>.asCars: List<Car>
        @JvmName("mapCarDtoListToCarList")
        get() = map { it.asCar }

    val CarStubDto.asCar: Car
        get() = Car(
            url = "$carApiUrl/cars/$id.json",
            id = id,
            name = name,
            shortDescription = shortDescription,
            description = null,
            imageUrl = null,
        )

    val List<CarStubDto>.asCars: List<Car>
        @JvmName("mapCarStubDtoListToCarList")
        get() = map { it.asCar }
}
