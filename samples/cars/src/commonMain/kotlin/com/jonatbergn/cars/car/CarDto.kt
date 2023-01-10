package com.jonatbergn.cars.car

import kotlinx.serialization.Serializable

@Serializable
internal data class CarDto(
    val id: Long,
    val name: String,
    val shortDescription: String? = null,
    val description: String? = null,
    val image: String? = null,
)
