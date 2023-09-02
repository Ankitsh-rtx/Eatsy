package com.example.eatsy.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatsy.R
import com.example.eatsy.adapter.RestaurantAdapter
import com.example.eatsy.adapter.TopDishAdapter
import com.example.eatsy.databinding.FragmentHomeBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseDB: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //instantiation of database
        firebaseDB  = FirebaseFirestore.getInstance()
        var restaurants: MutableList<Restaurants> = mutableListOf()
        firebaseDB.collection("restaurants").get().addOnSuccessListener { querySnapshot ->
            restaurants.clear()
            for (document in querySnapshot.documents){
                val obj = document.toObject(Restaurants::class.java)

                firebaseDB.collection("restaurants").document(document.id)
                    .collection("items").get().addOnSuccessListener {
                    for(item in it){
                        val menuItem = item.toObject(Item::class.java)
                        if (obj != null) {
                            obj.menuItemList?.add(menuItem)
                        }
                    }
                }
                restaurants.add(obj!!)
//                Log.d("firebase","${document.id} => ${document.data}")
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