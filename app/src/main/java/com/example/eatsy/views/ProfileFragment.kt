package com.example.eatsy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.eatsy.R
import com.example.eatsy.databinding.ActivityMainBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val navBar = activity!!.findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE

//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        activity?.window?.statusBarColor = context?.let { ContextCompat.getColor(it, R.color.ash_white) }!!

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


}