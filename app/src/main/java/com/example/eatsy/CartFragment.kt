package com.example.eatsy

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.adapter.CartViewAdapter
import com.example.eatsy.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        binding.cartItemsRecyclerview.adapter = CartViewAdapter(context)
        binding.cartItemsRecyclerview.layoutManager = LinearLayoutManager(context)
//        binding.cartItemsRecyclerview.addItemDecoration( DividerItemDecoration(getContext(),
//            DividerItemDecoration.VERTICAL)
//        )
        // Specify fixed size to improve performance
        binding.cartItemsRecyclerview.setHasFixedSize(true)
        binding.cartItemsRecyclerview.isNestedScrollingEnabled = false


        return binding.root
    }


}