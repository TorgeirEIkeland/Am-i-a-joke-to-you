package com.example.amiajoketoyouu.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.amiajoketoyouu.R
import com.example.amiajoketoyouu.ui.notifications.NotificationsFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cardviewAny: CardView
    private lateinit var cardviewDark: CardView
    private lateinit var cardviewSpooky: CardView
    private lateinit var cardviewPun: CardView
    private lateinit var cardviewProgramming: CardView
    private lateinit var cardviewMisc: CardView
    var navc : NavController ?= null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        cardviewAny = root.findViewById(R.id.imagecard_CategoryAny)
        cardviewDark= root.findViewById(R.id.imageCard_CategoryDark)
        cardviewMisc = root.findViewById(R.id.imageCard_CategoryMisc)
        cardviewPun = root.findViewById(R.id.imagecard_CategorPun)
        cardviewProgramming = root.findViewById(R.id.imageCard_CategoryProgramming)
        cardviewSpooky = root.findViewById(R.id.imagecard_CategorSpooky)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
          //  textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    SetonClicklistnerCard()
        }
    private fun SetonClicklistnerCard () {
        cardviewAny.setOnClickListener {

        }
        cardviewDark.setOnClickListener {

            }
        cardviewSpooky.setOnClickListener {

        }
        cardviewProgramming.setOnClickListener {

        }
        cardviewPun.setOnClickListener {

        }
        cardviewMisc.setOnClickListener {

        }
    }

    private fun navigateToJokePage() {
    }


}
