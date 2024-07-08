package com.rmf.testandroidmvvm.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmf.testandroidmvvm.data.remote.repository.JokeRepository
import com.rmf.testandroidmvvm.data.remote.response.Joke
import com.rmf.testandroidmvvm.util.Resource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository: JokeRepository = JokeRepository()

    private val _jokes = MutableLiveData<Resource<List<Joke>>>()
    val jokes: LiveData<Resource<List<Joke>>> get() = _jokes

    fun searchJokes(query: String) {
        viewModelScope.launch {
            repository.searchJokes(query).collect { result ->
                _jokes.value = result

            }
        }
    }
}