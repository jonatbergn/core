package com.jonatbergn.cars

import com.jonatbergn.cars.car.Car
import com.jonatbergn.core.iceandfire.foundation.entity.Page

object FakeCars {

    private val carStubs = (0..10).map {
        Car(
            url = "car$it", id = it.toLong(), name = "car#$it",
            shortDescription = null,
            description = null,
            imageUrl = null,
        )
    }

    val carStubsPage = Page(url = "page", next = null, data = carStubs)
}
