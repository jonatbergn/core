package com.jonatbergn.cars.car

import com.jonatbergn.core.iceandfire.foundation.entity.Entity

data class Car(
    override val url: String,
    val id: Long,
    val name: String,
    val shortDescription: String?,
    val description: String?,
    val imageUrl: String?,
) : Entity
