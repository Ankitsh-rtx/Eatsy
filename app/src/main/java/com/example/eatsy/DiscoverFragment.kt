package com.example.eatsy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.adapter.RestaurantAdapter
import com.example.eatsy.databinding.FragmentDiscoverBinding
import com.example.eatsy.databinding.FragmentHomeBinding


class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        binding.restaurantRecyclerview.adapter = RestaurantAdapter(context)
        binding.restaurantRecyclerview.layoutManager = LinearLayoutManager(context)
        // Specify fixed size to improve performance
        binding.restaurantRecyclerview.setHasFixedSize(true)
        binding.restaurantRecyclerview.isNestedScrollingEnabled = false
        return binding.root
    }


}