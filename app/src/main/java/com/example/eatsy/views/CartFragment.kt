package com.example.eatsy.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.CartViewAdapter
import com.example.eatsy.databinding.ActivityMainBinding
import com.example.eatsy.databinding.FragmentCartBinding
import com.example.eatsy.model.CartItem
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartItemList:HashMap<String,CartItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(
            requireContext(),
            R.color.white
        )
        requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar).visibility=View.VISIBLE

        val cartList = DataSource.orderList.second
        var cartListHM = this.arguments?.getSerializable("cartItems")

        cartItemList = if(cartListHM!=null){
            cartListHM as HashMap<String, CartItem>


        } else cartList

        if(cartItemList.size==0) {
            binding.emptyCart.visibility=View.VISIBLE
            binding.cartLayout.visibility=View.GONE
        }
        else binding.restaurantId.text = DataSource.orderList.first?.name.toString()

        binding.cartItemsRecyclerview.adapter = CartViewAdapter(context,cartItemList,binding)

        binding.cartItemsRecyclerview.layoutManager = LinearLayoutManager(context)

        // Specify fixed size to improve performance
        binding.cartItemsRecyclerview.setHasFixedSize(false)
        binding.cartItemsRecyclerview.isNestedScrollingEnabled = false


        //unhide navigation
        requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar).performShow()
        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE

        //payment fragment

        binding.proceedToPayTV.setOnClickListener{
            val bundle=Bundle()
            bundle.putString("Final Amount",(totalPrice()-70+80).toString())
            val paymentFragment=PaymentFragment()
            paymentFragment.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView,paymentFragment)?.addToBackStack(R.id.homeFragment.toString())
                ?.commit()
        }

        binding.goToMenu.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, DiscoverFragment())?.addToBackStack(R.id.homeFragment.toString())
                ?.commit()
        }
        binding.backStackBtn.setOnClickListener {
            //need to press twice to get back to previous fragment due to null added to backstack
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        binding.selectAddressBtn.setOnClickListener {
            Log.d("CartFragment", "onSelectAddressClick:  ${requireContext()}")
            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetDialog =
                LayoutInflater.from(context).inflate(R.layout.bottom_address_dialog, null)
            dialog.setContentView(bottomSheetDialog)
            dialog.show()

            val addAddress = activity?.findViewById<ConstraintLayout>(R.id.addAddress)
            addAddress?.setOnClickListener {
                //TODO: open dialog
            }
        }

        val total:Long=totalPrice()
        binding.totalPrice.text = "₹ "+total.toString()
        binding.payableAmount.text = "₹ "+ (total-70+80).toString()
        binding.finalAmount.text = "₹ "+ (total-70+80).toString()


        Log.d("cart","closed")
        val sharedPreferences=requireActivity().getSharedPreferences("CART", Context.MODE_PRIVATE).edit()
        sharedPreferences.putString("CART",DataSource.orderList.toString())
        sharedPreferences.commit()

        return binding.root
    }

    private fun totalPrice():Long{
        var totalPrice:Long = 0
        cartItemList.forEach { (key, value) -> totalPrice+= value.getItem().price
            ?.times(value.getItemQuantity()) ?: 0 }
        return totalPrice
    }

}