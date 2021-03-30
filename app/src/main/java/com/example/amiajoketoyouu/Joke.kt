package com.example.amiajoketoyouu

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

val gson = Gson()

class Joke(
        val error:Boolean,
        val category:String,
        val type:String,
        val joke:String? = null,
        val setup:String? = null,
        val delivery:String? = null,
        val flags:Flags,
        val id:Long,
        val safe:Boolean,
        val lang:String
    ) {

    companion object{
        public fun fromJson(jsonString:String) = gson.fromJson(jsonString, Joke::class.java)

        public fun urlCreator(category: String):String{
            return "https://v2.jokeapi.dev/joke/${category.toLowerCase().capitalize()}"
        }

        public fun fetchJoke(url:String, context: Context, callback:(Joke) -> Unit){
            val newJoke:Joke

            val queue = Volley.newRequestQueue(context)

            val stringRequest = StringRequest(Request.Method.GET, url, {
                    response ->
                val newJoke= fromJson(response)
                callback(newJoke)
                //Log.d("FOO", newJoke.joke.toString())
                Log.d("FOO", response)

            }, { Log.d("FOO", "Error occured") })

            queue.add(stringRequest)
        }
    }

}

data class Flags(
        val nsfw:Boolean,
        val religious:Boolean,
        val political:Boolean,
        val racist:Boolean,
        val sexist:Boolean,
        val explicit:Boolean
)