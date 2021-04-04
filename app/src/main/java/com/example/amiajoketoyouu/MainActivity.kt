package com.example.amiajoketoyouu

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    var category = "Any"
    var searchString:String? = null

    val gson = Gson()

    lateinit var sharedPrefs:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_jokes, R.id.navigation_favorites
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    fun setFragment(){
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.navigation_jokes)
    }

    fun saveOrDeleteJoke(jokeToSaveOrDelete:Joke, saveOrDelete: SaveOrDelete){
        val editor = sharedPrefs.edit()
        val jokes = sharedPrefs.getStringSet(getString(R.string.shared_pref_jokes), setOf())?.toMutableList()

        val jokeJson = gson.toJson(jokeToSaveOrDelete)

        if(saveOrDelete == SaveOrDelete.SAVE) jokes?.add(jokeJson)
        else if(saveOrDelete == SaveOrDelete.DELETE) jokes?.remove(jokeJson)

        editor.putStringSet(getString(R.string.shared_pref_jokes), jokes?.toSet())
        editor.commit()
    }

    fun jokeIsSaved(joke:Joke):Boolean{
        val jokes = sharedPrefs.getStringSet(getString(R.string.shared_pref_jokes), setOf())?.toMutableList()
        val jokeJson = gson.toJson(joke)
        if(jokes?.contains(jokeJson) == true) return true

        return false
    }


}