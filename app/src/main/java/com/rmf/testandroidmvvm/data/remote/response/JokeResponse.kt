package com.rmf.testandroidmvvm.data.remote.response

data class JokeResponse(
    val total: Int,
    val result: List<Joke>
)

data class Joke(
    val id: String,
    val value: String
)
