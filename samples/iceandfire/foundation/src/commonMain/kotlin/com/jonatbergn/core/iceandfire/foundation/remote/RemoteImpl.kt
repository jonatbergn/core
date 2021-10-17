package com.jonatbergn.core.iceandfire.foundation.remote

import com.jonatbergn.core.iceandfire.foundation.entity.Page
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.isSuccess

/**
 * An implementation of [Remote] using [HttpClient] to retrieve resources.
 *
 * @param client the client used for this repo
 * @param contentType the resource's [ContentType]
 * @param decodeOne closure used to decode a [String] to [T]\
 * @param decodeMany closure used to decode a [List] of [Strings] to [List] of [T]
 */
class RemoteImpl<T>(
    client: HttpClient,
    private val contentType: ContentType,
    private val decodeOne: String.() -> T,
    private val decodeMany: String.() -> List<T>,
) : Remote<T> {

    private val client = client.config { defaultRequest { accept(contentType) } }

    private suspend operator fun <T> String.invoke(block: suspend String.(Headers) -> T): T =
        client.get<HttpResponse>(this).run {
            if (status.isSuccess()) readText().block(headers) else error("Failed to GET $this")
        }

    override suspend fun getOne(url: String) = url { decodeOne() }
    override suspend fun getMany(url: String) = url { Page(url, decodeMany(), it.link().next) }
}
