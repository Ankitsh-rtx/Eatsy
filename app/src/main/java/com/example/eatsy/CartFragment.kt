package com.example.eatsy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatsy.adapter.CartViewAdapter
import com.example.eatsy.adapter.RestaurantAdapter
import com.example.eatsy.adapter.TopDishAdapter
import com.example.eatsy.databinding.CartviewItemLayoutBinding
import com.example.eatsy.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        binding.cartItemsRecyclerview.adapter = CartViewAdapter(context)
        binding.cartItemsRecyclerview.layoutManager = LinearLayoutManager(context)
        // Specify fixed size to improve performance
        binding.cartItemsRecyclerview.setHasFixedSize(true)
        binding.cartItemsRecyclerview.isNestedScrollingEnabled = false

        return binding.root
    }


}