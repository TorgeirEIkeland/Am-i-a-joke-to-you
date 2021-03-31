package com.example.amiajoketoyouu.ui.notifications

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.amiajoketoyouu.Joke
import com.example.amiajoketoyouu.R





class CustomAdapter(var dataSet: List<Joke>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textView: TextView

        init {
            // Define click listener for the ViewHolder's View.

            textView = view.findViewById(R.id.joke_textView)



        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.joke_adapter_view, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val thisJoke = dataSet[position]


        if(thisJoke.joke != null ){
            viewHolder.textView.text = thisJoke.joke
        }
        else {
            viewHolder.textView.text = thisJoke.setup + "\n" + thisJoke.delivery
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
    fun updateAdapter(newData: List<Joke>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    fun addInstance(list: List<Joke>) {
        updateAdapter(dataSet + list)
    }

}