package com.rmf.testandroidmvvm.data.remote

import com.rmf.testandroidmvvm.data.remote.response.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("jokes/search")
    suspend fun searchJokes(@Query("query") query: String): Response<JokeResponse>
}