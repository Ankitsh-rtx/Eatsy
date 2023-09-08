package com.example.eatsy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentPaymentBinding
import com.google.android.material.bottomappbar.BottomAppBar

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        val navBar = activity!!.findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE

        binding.backStackBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }
        binding.googlepay.isChecked = true

//        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
//            val selectedId = group.checkedRadioButtonId
//        }


        return binding.root
    }
}