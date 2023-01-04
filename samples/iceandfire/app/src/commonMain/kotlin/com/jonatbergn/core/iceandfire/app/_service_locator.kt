package com.jonatbergn.core.iceandfire.app

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
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.RepoImpl
import com.jonatbergn.core.iceandfire.foundation.local.LocalImpl
import com.jonatbergn.core.iceandfire.foundation.remote.RemoteImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.HttpTimeout.Plugin.INFINITE_TIMEOUT_MS
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface AppModule {

    val backgroundDispatcher: CoroutineDispatcher
    val iceAndFireUrl: String

    companion object Factory {

        operator fun invoke(
            backgroundDispatcher: CoroutineDispatcher,
            url: String = "https://www.anapioficeandfire.com/api",
        ): AppModule = AppModuleImpl(
            backgroundDispatcher = backgroundDispatcher,
            iceAndFireUrl = url,
        )
    }
}

interface AppContext : AppModule {

    val state: MutableStateFlow<State>
    val houseRepo: Repo<House>
    val characterRepo: Repo<Character>

    companion object Factory {
        operator fun invoke(
            module: AppModule,
        ): AppContext = AppContextImpl(
            module = module,
        )
    }
}

private class AppModuleImpl(
    override val backgroundDispatcher: CoroutineDispatcher,
    override val iceAndFireUrl: String,
) : AppModule

private class AppContextImpl(
    module: AppModule
) : AppContext, AppModule by module {

    private val client = HttpClient {
        /**
         * Not all platform http engines support timeouts.
         * In order to ensure the same behaviour on all platforms,
         * timeouts will be effective disabled by setting timeouts
         * to `INFINITE_TIMEOUT_MS`.
         */
        install(HttpTimeout) {
            requestTimeoutMillis = INFINITE_TIMEOUT_MS
            connectTimeoutMillis = INFINITE_TIMEOUT_MS
            socketTimeoutMillis = INFINITE_TIMEOUT_MS
        }
        install(HttpRequestRetry) {
            delayMillis { 1_000L }
            retryOnExceptionIf { _, cause -> cause is IOException }
        }
        defaultRequest {
            accept(ContentType("application", "vnd.anapioficeandfire+json").withParameter("version", "1"))
        }
    }

    private val localCharacters = LocalImpl<Character>()
    private val localHouses = LocalImpl<House>()
    private val remoteCharacters = RemoteImpl(
        client = client,
        decodeOne = { Json.decodeFromString<CharacterDto>(it).asCharacter },
        decodeMany = { Json.decodeFromString<List<CharacterDto>>(it).asCharacterList },
    )
    private val remoteHouses = RemoteImpl(
        client = client,
        decodeOne = { Json.decodeFromString<HouseDto>(it).asHouse },
        decodeMany = { Json.decodeFromString<List<HouseDto>>(it).asHouseList },
    )

    private suspend fun api(url: String) = Json.decodeFromString<ApiDto>(client.get(url).bodyAsText()).asApi(url)

    override val state = MutableStateFlow(State())

    override val houseRepo = RepoImpl(
        backgroundDispatcher,
        localHouses,
        remoteHouses,
    ) { "${api(iceAndFireUrl).houses}?page=1&pageSize=15" }

    override val characterRepo = RepoImpl(
        backgroundDispatcher,
        localCharacters,
        remoteCharacters,
    ) {
        "${api(iceAndFireUrl).characters}?page=1&p ageSize=15"
    }

//    override val fetchGrossHouseDependents = fetchHouseDependents.copy(
//        characters = { listOf(currentLord) },
//        houses = { listOf(overlord) },
//    )
}
