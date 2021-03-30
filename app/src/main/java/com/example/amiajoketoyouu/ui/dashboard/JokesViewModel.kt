package com.example.amiajoketoyouu.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.example.amiajoketoyouu.Joke
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class JokesViewModel : ViewModel() {

    val jokes: MutableList<Joke> = mutableListOf()
    val jokesLiveData = MutableLiveData<List<Joke>>()
    private val _currentJoke = MutableLiveData<Int>().apply {
        value = 0
    }
    val currentJoke:LiveData<Int> = _currentJoke

    fun getMoreJokes(url: String, queue: RequestQueue, errorCallback:() -> Unit){

        val stringRequest = StringRequest(Request.Method.GET, url,
        { response ->
            val listType:Type=object:TypeToken<List<Joke?>?>(){}.type
            val joke = Joke.fromJson(response)
            addJoke(joke)
            _currentJoke.value = jokes.size-1
        }, {
            errorCallback()
        })
        queue.add(stringRequest)
    }

    fun addJoke(joke:Joke){
        jokes.add(joke)
        jokesLiveData.value = jokes
    }

    fun changeCurrentJoke(intToChangeWith:Int){
        _currentJoke.value = _currentJoke.value?.plus(intToChangeWith)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is jokes Fragment"
    }
    val text: LiveData<String> = _text
}