package com.example.amiajoketoyouu

import android.util.Log
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

        public fun urlCreator(category: String, searchParameter:String?):String{
            return "https://v2.jokeapi.dev/joke/${category.toLowerCase().capitalize()}"
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