package com.example.eatsy.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.SearchAdapter
import com.example.eatsy.databinding.FragmentSearchBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        db = FirebaseFirestore.getInstance()
        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)

        navBar.visibility=View.GONE

        binding.backSearchBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        binding.searchResultRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.searchEdBtn.setOnClickListener {

            DataSource.itemSearchList.clear()
            val queryString = binding.searchEdittext.text?.trim().toString()
            binding.searchResultTv.text = "Showing results for '$queryString'"
            val items:MutableList<Item> = mutableListOf()
            db.collection("Items").whereLessThanOrEqualTo("name",queryString).get()
                .addOnSuccessListener { it ->
                    for(doc in it.documents){
                    val res = doc.toObject(Item::class.java)
                    if(res?.name?.lowercase(Locale.ROOT)?.contains(queryString) == true) {
                        items.add(res)
//                        Log.d("SearchFragment:", "doc ${res.name}")
                    }
                }
                    for (restaurant in DataSource.restaurants) {
//                        Log.d("SearchFragment", "restaurant:${restaurant.id} ")
                        val tmpItemList = mutableListOf<Item>()
                        restaurant.menus?.forEach {
                            for (item in items) {
                                if (item.id == it) {
                                    tmpItemList.add(item)
                                }
                            }
                            if (tmpItemList.isNotEmpty()) {
                                DataSource.itemSearchList.put(
                                    restaurant,
                                    tmpItemList
                                )
                            }
                        }
                    }
                    binding.resultLayout.visibility = View.VISIBLE
                    binding.recentSearchLayout.visibility = View.GONE
                    val adapter = SearchAdapter(requireContext(),DataSource.itemSearchList, requireActivity())
                    adapter.setOnClickListener(object : SearchAdapter.OnItemClickListener{
                        override fun onClick(restaurants: Restaurants) {
                            val args = Bundle()
                            args.putSerializable("res",restaurants)
                            val newFragment = RestaurantDetailsFragment()
                            newFragment.arguments = args
                            // Log.d("Restaurant Adapter", "onBindViewHolder: ${item.name}")

                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, newFragment).addToBackStack(null)
                                .commit()
                        }
                    })
                    binding.searchResultRecyclerview.adapter = adapter


            }.addOnFailureListener {
                Log.d("SearchFragment","failed due to $it")
            }

        }

        return binding.root
    }


}