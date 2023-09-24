package com.example.eatsy.views

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentPaymentBinding
import com.example.eatsy.databinding.FragmentSuccessBinding
import com.example.eatsy.services.OrderService
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SuccessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuccessFragment : Fragment() {
    private lateinit var binding: FragmentSuccessBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuccessBinding.inflate(layoutInflater)
        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE



        // clear data from shared preference on successfully order
        clearData()

        binding.orderId.text=this.arguments?.getString("ORDER_ID")
        val service= Intent(requireContext(), OrderService::class.java)
        service.putExtra("ORDER_ID",this.arguments?.getString("ORDER_ID"))
        requireActivity().startService(service)

//        requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        binding.trackOrder.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView,TrackOrderFragment())?.addToBackStack(R.id.homeFragment.toString())
                ?.commit()
        }
        return binding.root
    }
    private fun clearData(){
            val sharedPreferences = requireContext().getSharedPreferences("cart", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()
            editor?.apply()
    }
}