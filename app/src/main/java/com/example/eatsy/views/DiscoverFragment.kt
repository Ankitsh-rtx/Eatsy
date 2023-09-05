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
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.RestaurantAdapter
import com.example.eatsy.databinding.FragmentDiscoverBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.firestore.FirebaseFirestore


class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var firebaseDB:FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDiscoverBinding.inflate(layoutInflater)

        val navBar = activity!!.findViewById<BottomAppBar>(R.id.bottomAppBar)
        if(navBar.visibility==View.GONE)
            navBar.visibility = View.VISIBLE

        //instantiation of database
        firebaseDB  = FirebaseFirestore.getInstance()
        val restaurants: MutableList<Restaurants> = mutableListOf()
        firebaseDB.collection("restaurants").get().addOnSuccessListener { querySnapshot ->
            restaurants.clear()
            for (document in querySnapshot.documents){
                val obj = document.toObject(Restaurants::class.java)

//                firebaseDB.collection("restaurants").document(document.id)
//                    .collection("items").get().addOnSuccessListener {
//                        for(item in it){
//                            val menuItem = item.toObject(Item::class.java)
//                            if (obj != null) {
//                                obj.menuItemList?.add(menuItem)
//                            }
//                        }
//                    }
                restaurants.add(obj!!)
//                Log.d("firebase","${document.id} => ${document.data}")
            }
            val activity = activity
            binding.restaurantRecyclerview.adapter =
                activity?.let {
                    RestaurantAdapter(context,restaurants,
                        it
                    )
                }
//
        }.addOnFailureListener {
            Log.d("firebase", "onCreateView: error on loading data",it)
        }

        binding.restaurantRecyclerview.layoutManager = LinearLayoutManager(context)

        // Specify fixed size to improve performance
        binding.restaurantRecyclerview.setHasFixedSize(true)
        binding.restaurantRecyclerview.isNestedScrollingEnabled = false

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        return binding.root
    }


}