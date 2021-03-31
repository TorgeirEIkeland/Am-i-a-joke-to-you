package com.example.amiajoketoyouu.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.Volley
import com.example.amiajoketoyouu.Joke
import com.example.amiajoketoyouu.MainActivity
import com.example.amiajoketoyouu.R
import com.google.gson.Gson

class Jokesfragment : Fragment() {

    //Setup some values to be used
    private lateinit var jokesViewModel: JokesViewModel

    private lateinit var jokeSetup:TextView
    private lateinit var jokeDelivery:TextView

    private lateinit var previousButton: Button
    private lateinit var nextButton:Button

    private lateinit var favoriteButton: ImageButton
    private lateinit var shareButton: ImageButton

    //Create the fragment view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Define the view model and root
        jokesViewModel =
            ViewModelProvider(this).get(JokesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_jokes, container, false)

        //Define values
        jokeSetup = root.findViewById(R.id.joke_setup)
        jokeDelivery = root.findViewById(R.id.joke_delivery)

        previousButton = root.findViewById(R.id.button_previous)
        nextButton = root.findViewById(R.id.button_next)

        favoriteButton = root.findViewById(R.id.favorite_button)
        shareButton = root.findViewById(R.id.share_button)

        //Observe the current joke and jokes live data from the view model
        jokesViewModel.currentJoke.observe(viewLifecycleOwner, Observer { currentJoke ->
            jokesViewModel.jokesLiveData.observe(viewLifecycleOwner, Observer { jokes ->

                //Check if the current joke contains a joke or setup and delivery
                jokes[currentJoke].joke?.let{ joke ->
                    //Show the joke, make sure delivery is empty
                    jokeSetup.text = joke
                    jokeDelivery.text = ""
                } ?: run {
                    //Show setup and delivery
                    jokeSetup.text = jokes[currentJoke].setup
                    jokeDelivery.text = jokes[currentJoke].delivery
                }
            })
        })
        return root
    }

    //When view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Setup a request queue using Volley
        val queue = Volley.newRequestQueue(context)

        //Make sure we get a joke using the category and search parameters from the main activity
        jokesViewModel.getMoreJokes(Joke.urlCreator((activity as MainActivity).category, (activity as? MainActivity)?.searchString), queue) {}
        //Call function to set click listeners
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        //Make the current joke value go down unless we are at the first index
        previousButton.setOnClickListener{
            if(jokesViewModel.currentJoke.value != 0) jokesViewModel.changeCurrentJoke(-1)
            //Log.d("FOO", jokesViewModel.currentJoke.toString())
        }

        //Make the current joke value go up unless we are at the last index. If we are at last index, fetch new joke instead.
        nextButton.setOnClickListener{

            if(jokesViewModel.currentJoke.value!! < jokesViewModel.jokes.size - 1){
                jokesViewModel.changeCurrentJoke(1)
                //Log.d("FOO", jokesViewModel.currentJoke.toString())
            } else {
                val queue = Volley.newRequestQueue(context)
                jokesViewModel.getMoreJokes(Joke.urlCreator((activity as MainActivity).category, (activity as MainActivity).searchString), queue){}
            }
        }

        //Save the current joke to shared preferences
        favoriteButton.setOnClickListener(){

            val sharedprefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
            val editor = sharedprefs.edit()
            val gson = Gson()

            val jokes = sharedprefs.getStringSet(getString(R.string.shared_pref_jokes), setOf())?.toMutableList()

            if(jokesViewModel.currentJoke.value != null){
                val jokeJson = gson.toJson(jokesViewModel.jokes[jokesViewModel.currentJoke.value!!])
                jokes?.add(jokeJson)
            }
            editor.putStringSet(getString(R.string.shared_pref_jokes), jokes?.toSet())
            editor.commit()
        }
    }
}