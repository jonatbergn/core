package com.jonatbergn.core.iceandfire.app.house.interact

sealed class LoadNextHousesEvent {

    object InFlight : LoadNextHousesEvent()
    object Complete : LoadNextHousesEvent()
}
