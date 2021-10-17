package com.jonatbergn.core.iceandfire.android.nav

import androidx.navigation.NavHostController
import com.jonatbergn.core.iceandfire.android.nav.ArgKeys.HouseUrl
import com.jonatbergn.core.iceandfire.android.nav.Destinations.House

object ArgKeys {
    const val HouseUrl = "house_url"
}

object Destinations {
    const val Houses = "houses"
    const val House = "house?url={$HouseUrl}"
}

class Navigation(navController: NavHostController) {
    val house = { url: String ->
        navController.navigate(route = House.replace("{$HouseUrl}", url))
    }
}
