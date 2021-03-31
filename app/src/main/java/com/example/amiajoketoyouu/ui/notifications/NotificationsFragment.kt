package com.example.amiajoketoyouu.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amiajoketoyouu.Flags
import com.example.amiajoketoyouu.Joke
import com.example.amiajoketoyouu.MainActivity
import com.example.amiajoketoyouu.R
import com.google.gson.Gson

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    var customAdapter: CustomAdapter? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorites, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)

        return root
    }

    private fun initRecyclerView() {
        customAdapter = CustomAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = customAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)

        convertSavedJokes()
    }


    fun convertSavedJokes(){
        val sharedprefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        val jokeList = sharedprefs.getStringSet(getString(R.string.shared_pref_jokes), setOf())?.toMutableList()
        val editor = sharedprefs.edit()
        editor.remove(getString(R.string.shared_pref_jokes))


        var jokes = mutableListOf<Joke>()

        if(jokeList != null) {
            for (joke in jokeList) {

                val currentJoke = Joke.fromJson(joke)
                jokes.add(currentJoke)
            }
        }

        val savedJokes = jokes
        customAdapter?.updateAdapter(savedJokes)
    }


    fun removeJoke(newList: MutableList<Joke>){
        val sharedprefs = PreferenceManager.getDefaultSharedPreferences((activity as? MainActivity)?.applicationContext)
        val editor = sharedprefs.edit()
        val gson = Gson()

        val jokes = mutableListOf<String>()

        for(joke in newList){
            var jokeJson = gson.toJson(joke)
            jokes?.add(jokeJson)
        }

        editor.putStringSet(getString(R.string.shared_pref_jokes), jokes?.toSet())

        editor.commit()
        convertSavedJokes()
    }
}