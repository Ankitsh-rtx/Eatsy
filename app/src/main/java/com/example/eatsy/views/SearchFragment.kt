package com.example.eatsy.views

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.provider.UserDictionary.Words.LOCALE
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentSearchBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
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

        binding.backSearchBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        binding.searchEdBtn.setOnClickListener {
            Log.d("SearchFragment", "clicked")
            val queryString = binding.searchEdittext.text?.trim().toString()
            var items:MutableList<Item> = mutableListOf()
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
                    for (pair in DataSource.itemSearchList) {
                            Log.d("SearchFragment:", "${pair.key}: ${pair.value}")
                    }

            }.addOnFailureListener {
                Log.d("SearchFragment","failed due to $it")
            }

        }


        return binding.root
    }

}