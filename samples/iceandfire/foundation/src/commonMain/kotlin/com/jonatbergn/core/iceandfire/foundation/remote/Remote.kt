package com.jonatbergn.core.iceandfire.foundation.remote

import com.jonatbergn.core.iceandfire.foundation.entity.Page

/**
 * Interface to access remote resources of type [T] by either querying a single resource, or a [Page] containing
 * multiple resources.
 */
interface Remote<T> {

    /**
     * Query a single resource.
     *
     * @param url the url which resolves a specific resource of type [T]
     */
    suspend fun getOne(url: String): T

    /**
     * Query a page of resources
     *
     * @param url the url resolving the page of resouerces of type [T]
     */
    suspend fun getPage(url: String): Page<T>
}
