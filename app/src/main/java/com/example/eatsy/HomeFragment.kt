package com.example.eatsy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatsy.Adapter.TopDishAdapter
import com.example.eatsy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.dishesRecyclerview.adapter = TopDishAdapter(context)
        binding.dishesRecyclerview.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.HORIZONTAL)


        // Specify fixed size to improve performance
        binding.dishesRecyclerview.setHasFixedSize(true)






        return binding.root
    }

}