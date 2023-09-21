package com.example.eatsy.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentSearchBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.google.firebase.firestore.FirebaseFirestore

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
            db.collection("Items").whereEqualTo("name",queryString).get()
                .addOnSuccessListener {
                for(doc in it.documents){
                    val res = doc.toObject(Item::class.java)
                    Log.d("SearchFragment:","doc ${res?.name}")
                }
            }.addOnFailureListener {
                Log.d("SearchFragment","failed due to $it")
            }
        }


        return binding.root
    }

}