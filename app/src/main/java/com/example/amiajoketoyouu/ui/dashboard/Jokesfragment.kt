package com.example.amiajoketoyouu.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.Volley
import com.example.amiajoketoyouu.Joke
import com.example.amiajoketoyouu.MainActivity
import com.example.amiajoketoyouu.R

class Jokesfragment : Fragment() {

    var category = "Any"

    private lateinit var jokeSetup:TextView
    private lateinit var jokeDelivery:TextView

    private lateinit var jokesViewModel: JokesViewModel

    private lateinit var previousButton: Button
    private lateinit var nextButton:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        jokesViewModel =
            ViewModelProvider(this).get(JokesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_jokes, container, false)

        jokeSetup = root.findViewById(R.id.joke_setup)
        jokeDelivery = root.findViewById(R.id.joke_delivery)

        previousButton = root.findViewById(R.id.button_previous)
        nextButton = root.findViewById(R.id.button_next)

        jokesViewModel.currentJoke.observe(viewLifecycleOwner, Observer { currentJoke ->
            jokesViewModel.jokesLiveData.observe(viewLifecycleOwner, Observer { jokes ->

                if(jokes[currentJoke].joke != null){
                    jokeSetup.text = jokes[currentJoke].joke
                    jokeDelivery.text = ""
                }
                else {
                    jokeSetup.text = jokes[currentJoke].setup
                    jokeDelivery.text = jokes[currentJoke].delivery
                }

            })
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val queue = Volley.newRequestQueue(context)

        jokesViewModel.getMoreJokes(Joke.urlCreator(MainActivity().category), queue) {}
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        previousButton.setOnClickListener{
            if(jokesViewModel.currentJoke.value != 0) jokesViewModel.changeCurrentJoke(-1)
            //Log.d("FOO", jokesViewModel.currentJoke.toString())
        }

        nextButton.setOnClickListener{

            if(jokesViewModel.currentJoke.value!! < jokesViewModel.jokes.size - 1){
                jokesViewModel.changeCurrentJoke(1)
                //Log.d("FOO", jokesViewModel.currentJoke.toString())
            } else {
                val queue = Volley.newRequestQueue(context)
                jokesViewModel.getMoreJokes(Joke.urlCreator(MainActivity().category), queue){}
            }
        }
    }
}