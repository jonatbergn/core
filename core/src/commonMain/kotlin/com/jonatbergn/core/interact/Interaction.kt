package com.jonatbergn.core.interact

interface Interaction<Result> {
    suspend operator fun invoke(): Result
}
