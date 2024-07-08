package com.rmf.testandroidmvvm.data.remote.repository

import androidx.lifecycle.MutableLiveData
import com.rmf.testandroidmvvm.data.remote.Api
import com.rmf.testandroidmvvm.data.remote.RetrofitClient
import com.rmf.testandroidmvvm.data.remote.response.Joke
import com.rmf.testandroidmvvm.data.remote.response.JokeResponse
import com.rmf.testandroidmvvm.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class JokeRepository {
    private val apiService: Api = RetrofitClient.retrofitInstance.create(Api::class.java)

    suspend fun searchJokes(query: String): Flow<Resource<List<Joke>>> {
        return flow {
            emit(Resource.Loading(true))
            val result = try {
                val response = apiService.searchJokes(query)
                when {
                    response.isSuccessful -> {
                        response.body()?.result
                    }

                    else -> {
                        emit(Resource.Error(message = "Terjadi Kesalahan"))
                        null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Terjadi Kesalahan"))
                null
            }
            emit(Resource.Loading(false))
            result?.let { list ->
                emit(Resource.Success(list))
            }
        }
    }
}