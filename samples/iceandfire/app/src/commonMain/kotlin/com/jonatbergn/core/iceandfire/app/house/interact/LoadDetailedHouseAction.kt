package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.foundation.interact.LoadDetailedEntityAction

/**
 * Action to trigger the [LoadDetailedHouse] use case.
 *
 * @param url the resource url of the house for which the details should be loaded
 */
data class LoadDetailedHouseAction(override val url: String) : LoadDetailedEntityAction
