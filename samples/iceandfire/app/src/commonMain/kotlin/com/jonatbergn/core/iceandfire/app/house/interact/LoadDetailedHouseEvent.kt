package com.jonatbergn.core.iceandfire.app.house.interact

sealed class LoadDetailedHouseEvent {

    object InFlight : LoadDetailedHouseEvent()
    object Complete : LoadDetailedHouseEvent()
}
