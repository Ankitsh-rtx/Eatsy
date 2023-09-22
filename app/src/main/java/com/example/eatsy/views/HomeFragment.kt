package com.example.eatsy.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.RestaurantAdapter
import com.example.eatsy.adapter.TopDishAdapter
import com.example.eatsy.databinding.FragmentHomeBinding
import com.example.eatsy.model.Restaurants
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var restaurants: MutableList<Restaurants>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.hideOnScroll=true
        navBar.visibility=View.VISIBLE

        // instantiation of database
        firebaseDB  = FirebaseFirestore.getInstance()
        // instantiation of the restaurants list that stores the values received from firebase


        firebaseDB.collection("restaurants").get().addOnSuccessListener { querySnapshot ->
            DataSource.restaurants.clear()
            for (document in querySnapshot.documents) {
                val res = document.toObject(Restaurants::class.java)
                DataSource.restaurants.add(res!!)
            }
            val activity = activity
            binding.restaurantRecyclerview.adapter =
                activity?.let {
                    RestaurantAdapter(context,DataSource.restaurants,
                        it
                    )
                }

        }.addOnFailureListener {
            Log.d("firebase", "onCreateView: error on loading data",it)
        }
        //Pop all backstack once Home Fragment is reached..
        activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        binding.dishesRecyclerview.adapter = TopDishAdapter(context)
        binding.dishesRecyclerview.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.HORIZONTAL)
        // Specify fixed size to improve performance
        binding.dishesRecyclerview.setHasFixedSize(true)
        binding.restaurantRecyclerview.layoutManager = LinearLayoutManager(context)
        // Specify fixed size to improve performance
        binding.restaurantRecyclerview.setHasFixedSize(true)
        binding.restaurantRecyclerview.isNestedScrollingEnabled = false

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)


        binding.profileImage.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, ProfileFragment())?.addToBackStack(R.id.homeFragment.toString())
                ?.commit()
        }

        binding.searchEdittext.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView,SearchFragment())
                .addToBackStack(null)
                .commit()
            navBar.visibility = View.GONE
        }

        return binding.root
    }


}