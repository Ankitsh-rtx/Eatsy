package com.example.eatsy.views

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.provider.UserDictionary.Words.LOCALE
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.SearchAdapter
import com.example.eatsy.adapter.SearchAdapterChildItems
import com.example.eatsy.databinding.FragmentSearchBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.LocalCacheSettings
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
            Log.d("SearchFragment", "clicked")
            DataSource.itemSearchList.clear()
            val queryString = binding.searchEdittext.text?.trim().toString()
            binding.searchResultTv.text = "Showing results for '$queryString'"
            val items:MutableList<Item> = mutableListOf()
            db.collection("Items").whereLessThanOrEqualTo("name",queryString).get()
                .addOnSuccessListener {
                for(doc in it.documents){
                    val res = doc.toObject(Item::class.java)
                    if(res?.name?.lowercase(Locale.ROOT)?.contains(queryString) == true) {
                        items.add(res)
                        Log.d("SearchFragment:", "doc ${res.name}")
                    }
                }
                    for (restaurant in DataSource.restaurants) {

                        restaurant.menus?.forEach {
                            var tmpItemList = mutableListOf<Item>()
                            for (item in items) {
                                if (item.id == it) {
                                        tmpItemList.add(item)
                                }
                            }
                            if (tmpItemList.isNotEmpty()) DataSource.itemSearchList.put(
                                    restaurant,
                                    tmpItemList
                            )
                        }
                    }
                    binding.resultLayout.visibility = View.VISIBLE
                    binding.recentSearchLayout.visibility = View.INVISIBLE
                    val adapter = SearchAdapter(requireContext(),DataSource.itemSearchList)
                    binding.searchResultRecyclerview.adapter = adapter

            }.addOnFailureListener {
                Log.d("SearchFragment","failed due to $it")
            }

        }

        return binding.root
    }

}