package com.jonatbergn.core.iceandfire.android.house

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jonatbergn.core.iceandfire.android.nav.ArgKeys
import com.jonatbergn.core.iceandfire.android.nav.Destinations
import com.jonatbergn.core.iceandfire.android.nav.Navigation
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

@Composable
fun HouseUi(
    navController: NavHostController = rememberNavController(),
    navigation: Navigation = remember { Navigation(navController) },
) {
    NavHost(navController = navController, startDestination = Destinations.Houses) {
        composable(
            Destinations.Houses
        ) {
            HouseListUi(
                onSelectHouseUrl = { navigation.house(it) }
            )
        }
        composable(
            Destinations.House,
            listOf(navArgument(ArgKeys.HouseUrl) { type = NavType.StringType })
        ) {
            HouseDetailsUi(
                house = Pointer(it.arguments?.getString(ArgKeys.HouseUrl)!!),
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
