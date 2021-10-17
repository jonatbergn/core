package com.jonatbergn.core.iceandfire.app

import com.jonatbergn.core.exception.async
import com.jonatbergn.core.exception.withExceptionHandling
import com.jonatbergn.core.iceandfire.app.api.ApiDto
import com.jonatbergn.core.iceandfire.app.api.asApi
import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.character.CharacterDto
import com.jonatbergn.core.iceandfire.app.character.asCharacter
import com.jonatbergn.core.iceandfire.app.character.asCharacterList
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.house.HouseDto
import com.jonatbergn.core.iceandfire.app.house.asHouse
import com.jonatbergn.core.iceandfire.app.house.asHouseList
import com.jonatbergn.core.iceandfire.app.house.interact.LoadDetailedHouse
import com.jonatbergn.core.iceandfire.app.house.interact.LoadNextHouses
import com.jonatbergn.core.iceandfire.app.house.interact.ObserveHouses
import com.jonatbergn.core.iceandfire.foundation.entity.RepoImpl
import com.jonatbergn.core.iceandfire.foundation.local.LocalImpl
import com.jonatbergn.core.iceandfire.foundation.remote.RemoteImpl
import com.jonatbergn.core.model.Model
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.utils.io.errors.IOException
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface IceAndFireContext {

    val model: Model<State>
}

@OptIn(
    ExperimentalSerializationApi::class,
    ExperimentalTime::class
)
internal class IceAndFireContextImpl(
    scope: CoroutineScope,
) : IceAndFireContext {

    private val retry: suspend (Throwable, Long) -> Boolean = { cause, _ ->
        if (cause is IOException) {
            delay(seconds(1))
            true
        } else {
            false
        }
    }
    private val client = HttpClient()
    private val contentType = ContentType.parse(
        "application/vnd.anapioficeandfire+json"
    ).withParameter("version", "1")
    private val url = "https://www.anapioficeandfire.com/api"
    private val api = scope.async(
        start = LAZY,
        value = {
            RemoteImpl(
                client = client,
                contentType = contentType,
                decodeOne = { Json.decodeFromString<ApiDto>(this).asApi(url) },
                decodeMany = { throw NotImplementedError() },
            ).getOne(url)
        }
    ) { retryWhen(retry) }
    private val remoteCharacters = RemoteImpl(
        client = client,
        contentType = contentType,
        decodeOne = { Json.decodeFromString<CharacterDto>(this).asCharacter },
        decodeMany = { Json.decodeFromString<List<CharacterDto>>(this).asCharacterList },
    )
    private val localCharacters = LocalImpl<Character>()
    private val remoteHouses = RemoteImpl(
        client = client,
        contentType = contentType,
        decodeOne = { Json.decodeFromString<HouseDto>(this).asHouse },
        decodeMany = { Json.decodeFromString<List<HouseDto>>(this).asHouseList }
    )
    private val localHouses = LocalImpl<House>()
    private val fetchHouseDependents = FetchDependentsImpl<House>(
        remoteCharacters,
        remoteHouses,
        localCharacters,
        localHouses
    )
    private val houseRepo = RepoImpl(
        scope,
        remoteHouses,
        localHouses,
        fetchGross = fetchHouseDependents.copy(
            characters = { listOf(currentLord) },
            houses = { listOf(overlord) }
        ),
        fetchDetails = fetchHouseDependents.copy(
            characters = { listOf(heir, founder) + swornMembers },
            houses = { listOf(overlord) + cadetBranches }
        )
    ) { "${api.await().houses}?page=1&pageSize10" }
    override val model = Model(
        scope,
        State(),
        useCases = listOf(
            ObserveHouses(houseRepo),
            LoadNextHouses(houseRepo).withExceptionHandling { retryWhen(retry) },
            LoadDetailedHouse(houseRepo).withExceptionHandling { retryWhen(retry) },
        ),
        reducers = listOf(
            ObserveHouses.reducer,
            LoadNextHouses.reducer,
            LoadDetailedHouse.reducer
        )
    )
}

fun iceAndFireContext(scope: CoroutineScope): IceAndFireContext = IceAndFireContextImpl(scope)
