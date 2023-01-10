package com.jonatbergn.cars

import com.jonatbergn.cars.booking.Booking
import com.jonatbergn.cars.car.Car
import com.jonatbergn.cars.car.CarDto
import com.jonatbergn.cars.car.CarMapping
import com.jonatbergn.cars.car.CarStubDto
import com.jonatbergn.cars.reservation.Reservation
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.RepoImpl
import com.jonatbergn.core.iceandfire.foundation.local.LocalImpl
import com.jonatbergn.core.iceandfire.foundation.remote.Remote
import com.jonatbergn.core.iceandfire.foundation.remote.RemoteImpl
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.toInstant
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.seconds

interface AppModule {

    val dispatcher: CoroutineDispatcher
    val apiUrl: String

    companion object Factory {

        operator fun invoke(
            dispatcher: CoroutineDispatcher,
            url: String = "http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api",
        ): AppModule = AppModuleImpl(
            dispatcher = dispatcher,
            apiUrl = url,
        )
    }
}

interface AppContext : AppModule {

    val state: MutableStateFlow<State>
    val carRepo: Repo<Car>
    val carStubRepo: Repo<Car>
    val bookingRepo: Repo<Booking>

    suspend fun createBooking(reservation: Reservation): Pointer<Booking>

    companion object Factory {
        operator fun invoke(
            module: AppModule,
        ): AppContext = AppContextImpl(
            module = module,
        )
    }
}

private class AppModuleImpl(
    override val dispatcher: CoroutineDispatcher,
    override val apiUrl: String,
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
            requestTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            connectTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
            socketTimeoutMillis = HttpTimeout.INFINITE_TIMEOUT_MS
        }

        /**
         * Every http request will be retried with a delay in case network
         * errors occurred.
         */
        install(HttpRequestRetry) {
            delayMillis { 1_000L }
            retryOnException()
        }

        /**
         * The entire api uses json as data interchange format.
         */
        defaultRequest { accept(ContentType.Application.Json) }
    }

    private val carMapping = CarMapping(apiUrl)
    private var bookingCounter = 0

    /**
     * Keep a reference to local booking storage, since we need to manipulate
     * it whenever a new booking is created. Fake bookings get created locally
     * until the remote api is implemented.
     */
    private val bookingLocal = LocalImpl<Booking>()

    override val state = MutableStateFlow(State())

    override val carRepo = RepoImpl(
        dispatcher = dispatcher,
        local = LocalImpl(),
        remote = RemoteImpl(
            client = client,
            decodeOne = { with(carMapping) { Json.decodeFromString<CarDto>(it).asCar } },
            decodeMany = { with(carMapping) { Json.decodeFromString<List<CarDto>>(it).asCars } }
        ),
    ) { error("Fetching a page of detailed cars is not supported.") }

    override val carStubRepo = RepoImpl(
        dispatcher = dispatcher,
        local = LocalImpl(),
        remote = RemoteImpl(
            client = client,
            decodeOne = { with(carMapping) { Json.decodeFromString<CarStubDto>(it).asCar } },
            decodeMany = { with(carMapping) { Json.decodeFromString<List<CarStubDto>>(it).asCars } }
        ),
    ) { "$apiUrl/cars.json" }

    override val bookingRepo = RepoImpl(
        dispatcher = dispatcher,
        local = bookingLocal,
        /**
         * The remote booking api is not implemented yet.
         * The following remote implementation provides
         * static, fake booking data.
         */
        remote = object : Remote<Booking> {

            private val booking1 = Booking(
                url = "booking${bookingCounter++}",
                created = Clock.System.now(),
                car = Pointer("http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/cars/6.json"),
                start = "2023-01-11T10:00:00Z".toInstant(),
                duration = 3.days,
            )

            override suspend fun getOne(url: String) = booking1
            override suspend fun getPage(url: String) = Page(
                url = "",
                next = null,
                data = listOf(booking1)
            )
        }
    ) { "irrelevant" }

    override suspend fun createBooking(reservation: Reservation): Pointer<Booking> {
        // simulate a remote call for creating a booking
        delay(3.seconds)
        val booking = Booking(
            url = "booking${bookingCounter++}",
            created = Clock.System.now(),
            car = reservation.car,
            start = reservation.start,
            duration = reservation.duration,
        )
        bookingLocal.put(booking)
        return Pointer(booking.url)
    }
}
