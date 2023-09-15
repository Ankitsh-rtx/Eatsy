package com.example.eatsy.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentPaymentBinding
import com.example.eatsy.model.Order
import com.example.eatsy.model.CartItem
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.Date
import java.util.SimpleTimeZone

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE

        binding.backStackBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }
        binding.googlepay.isChecked = true

        binding.finalAmount.text="₹ "+this.arguments?.getString("Final Amount")

//        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
//            val selectedId = group.checkedRadioButtonId
//        }

        // place order

        binding.proceedToPayTV.setOnClickListener{

            binding.proceedToPayTV.isClickable=false
            binding.progressBar.visibility = View.VISIBLE
            val firebaseDB  = FirebaseFirestore.getInstance()
            val Order=Order(
                DataSource!!.user!!.id.toString(),
                DataSource.orderList.first?.id.toString(),
                DataSource.orderList.second.values.toList(),
                Timestamp( Date().time),
                this.arguments?.getString("Final Amount")?.toInt(),
                0,
                DataSource.orderAddress,
                0,
                "")
            Log.d("order",Order.toString())
            firebaseDB.collection("orders").add(Order).addOnSuccessListener { document->
                val bundle=Bundle()
                bundle.putString("ORDER_ID", document.id)
                val successFragment=SuccessFragment()
                successFragment.arguments=bundle
                // order list cleared on order success
                DataSource.orderList= Pair(null,HashMap<String,CartItem>())


                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView,successFragment)?.addToBackStack(null)
                    ?.commit()


                binding.progressBar.visibility = View.GONE
            }
        }
        return binding.root
    }
}