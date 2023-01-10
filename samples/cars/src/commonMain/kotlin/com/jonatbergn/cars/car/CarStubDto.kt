package com.jonatbergn.cars.car

import kotlinx.serialization.Serializable

@Serializable
internal data class CarStubDto(
    val id: Long,
    val name: String,
    val shortDescription: String? = null,
)
