package com.jonatbergn.core.iceandfire.foundation.entity

/**
 * A class holding information about depending entities
 *
 * @param url the identifier of the depending entity
 * @param fetched
 *  - null, if the dependent entity was not fetched yet
 *  - true, if the dependent entity was fetched
 *  - false, if the dependent entity could not be fetched
 *  @param value the dependent possibly fetched entity
 */
data class Dependent<T : Entity>(
    val url: String?,
    var fetched: Boolean? = null,
    var value: T? = null,
) {

    companion object {
        /**
         * Maps a string representing a url to a dependent entity
         */
        fun <T : Entity> String?.asDependent(): Dependent<T> = Dependent(this)

        /**
         * Maps a list of strings representing a list of urls to depending entities
         */
        fun <T : Entity> List<String>?.asDependents(): List<Dependent<T>> =
            orEmpty().map { it.asDependent() }
    }
}
