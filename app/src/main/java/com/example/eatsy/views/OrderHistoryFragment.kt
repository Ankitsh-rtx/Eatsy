package com.example.eatsy.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.R
import com.example.eatsy.adapter.OrderHistoryAdapter
import com.example.eatsy.databinding.FragmentOrderHistoryBinding
import com.example.eatsy.model.Order
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.math.log

class OrderHistoryFragment : Fragment() {
    private lateinit var binding:FragmentOrderHistoryBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var orderList:MutableList<Order>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderHistoryBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        orderList = mutableListOf()

        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE

        binding.imageView24.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        CoroutineScope(Dispatchers.IO).launch {
            loadOrderHistory(db,firebaseAuth,orderList).await()
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(),"orders->${orderList.size}",Toast.LENGTH_SHORT).show()
                binding.recyclerView.adapter = OrderHistoryAdapter(orderList)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        return binding.root
    }
}
private  fun  loadOrderHistory(
    db: FirebaseFirestore,
    firebaseAuth: FirebaseAuth,
    orderList:MutableList<Order>): Task<QuerySnapshot> {

    val ord = db.collection("orders")
        .whereEqualTo("userId",firebaseAuth.currentUser!!.uid)
        .get().addOnSuccessListener {doc->
            Log.d("TRY", "loadOrderHistory: "+doc.documents.size.toString())
            doc.documents.forEach { orders ->
                Log.d("TRY", "loadOrderHistory: "+orders.toString())
                val res = orders.toObject(Order::class.java)
                if (res != null) {
                    orderList.add(res)
                }
            }
        }.addOnFailureListener {
            Log.d("ProfileFragment:", "$it ")
        }
    return ord
}


