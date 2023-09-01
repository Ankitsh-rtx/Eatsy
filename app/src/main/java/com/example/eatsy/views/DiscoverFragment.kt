package com.example.eatsy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.RestaurantAdapter
import com.example.eatsy.databinding.FragmentDiscoverBinding
import com.example.eatsy.model.Restaurants


class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        binding.restaurantRecyclerview.adapter = RestaurantAdapter(context, DataSource.restaurants)
        binding.restaurantRecyclerview.layoutManager = LinearLayoutManager(context)
        // Specify fixed size to improve performance
        binding.restaurantRecyclerview.setHasFixedSize(true)
        binding.restaurantRecyclerview.isNestedScrollingEnabled = false

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        return binding.root
    }


}