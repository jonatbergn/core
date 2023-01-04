package com.jonatbergn.core.iceandfire.app.house

import com.jonatbergn.core.iceandfire.foundation.entity.Page
import kotlinx.collections.immutable.persistentListOf

object FakeHouses {

    private val houseTemplate = House(
        url = "",
        name = null,
        region = null,
        coatOfArms = null,
        words = null,
        titles = null,
        seats = null,
        founded = null,
        diedOut = null,
        ancestralWeapons = null,
        heir = null,
        currentLord = null,
        founder = null,
        swornMembers = null,
        overlord = null,
        cadetBranches = null,
    )

    val house01 = houseTemplate.copy(url = "house01")
    val house02 = houseTemplate.copy(url = "house02")
    val house03 = houseTemplate.copy(url = "house03")
    val house04 = houseTemplate.copy(url = "house04")
    val house05 = houseTemplate.copy(url = "house05")
    val house06 = houseTemplate.copy(url = "house06")
    val house07 = houseTemplate.copy(url = "house07")
    val house08 = houseTemplate.copy(url = "house08")
    val house09 = houseTemplate.copy(url = "house09")

    val housePage01 = Page(
        url = "page01", next = "page02", data = persistentListOf(
            house01,
            house02,
            house03,
        )
    )
    val housePage02 = Page(
        url = "page02", next = "page03", data = persistentListOf(
            house04,
            house05,
            house06,
        )
    )
    val housePage03 = Page(
        url = "page03", next = null, data = persistentListOf(
            house07,
            house08,
            house09,
        )
    )
    val housePages = listOf(housePage01, housePage02, housePage03)
}
