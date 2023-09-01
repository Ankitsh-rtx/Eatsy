package com.example.eatsy.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.RestaurantAdapter
import com.example.eatsy.adapter.TopDishAdapter
import com.example.eatsy.databinding.FragmentHomeBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseDB: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        Log.d("HomeFragment", "onCreateView: before calling data from firebase")
        //instantiation of database

        firebaseDB  = FirebaseFirestore.getInstance()
        var restaurants: MutableList<Restaurants> = mutableListOf()
        firebaseDB.collection("restaurants").get().addOnSuccessListener {
            restaurants.clear()
            for (document in it.documents){
                val obj = document.toObject(Restaurants::class.java)
//                val items = document.toObject(Restaurants::class.java).
//                val item = Item(items?.)
                restaurants.add(obj!!)
//                restaurants[0].menuItemList = DataSource.items
                Log.d("firebase","${document.id} => ${document.data}")
            }
            binding.restaurantRecyclerview.adapter = RestaurantAdapter(context,restaurants)
        }.addOnFailureListener {
            Log.d("firebase", "onCreateView: error on loading data",it)
        }

        binding.dishesRecyclerview.adapter = TopDishAdapter(context)
        binding.dishesRecyclerview.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.HORIZONTAL)
        // Specify fixed size to improve performance
        binding.dishesRecyclerview.setHasFixedSize(true)
//        binding.restaurantRecyclerview.adapter = RestaurantAdapter(context)
        binding.restaurantRecyclerview.layoutManager = LinearLayoutManager(context)
        // Specify fixed size to improve performance
        binding.restaurantRecyclerview.setHasFixedSize(true)
        binding.restaurantRecyclerview.isNestedScrollingEnabled = false

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)



        return binding.root
    }

}