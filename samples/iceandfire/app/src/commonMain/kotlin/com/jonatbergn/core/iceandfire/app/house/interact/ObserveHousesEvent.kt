package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection

data class ObserveHousesEvent(val collection: PageCollection<House>)
