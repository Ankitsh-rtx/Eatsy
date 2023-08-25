package com.example.eatsy.views

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.CartViewAdapter
import com.example.eatsy.databinding.FragmentCartBinding
import com.example.eatsy.model.CartItem


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
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),
            R.color.off_white
        )

        val cartList = DataSource.orderList
        var cartListHM = this.arguments?.getSerializable("cartItems")

        if(cartListHM!=null){
            cartItemList = cartListHM as HashMap<String, CartItem>

        }
        else cartItemList = cartList

        if(cartItemList.size==0) {
            binding.emptyCart.visibility=View.VISIBLE
            binding.cartLayout.visibility=View.GONE
            binding.goToMenu.setOnClickListener(object:View.OnClickListener{
                override fun onClick(p0: View?) {
                    var intent= Intent(context,MainActivity::class.java)
                    intent.putExtra("discover","discover")
                    startActivity(intent)
                }
            })
        }

        binding.cartItemsRecyclerview.adapter = CartViewAdapter(context,cartItemList,binding)
        binding.cartItemsRecyclerview.layoutManager = LinearLayoutManager(context)
            // Specify fixed size to improve performance
        binding.cartItemsRecyclerview.setHasFixedSize(false)
        binding.cartItemsRecyclerview.isNestedScrollingEnabled = false


        val total:Long=totalPrice()
        binding.totalPrice.text = "₹ "+total.toString()

        binding.payableAmount.text = "₹ "+ (total-70+80).toString()
        binding.finalAmount.text = "₹ "+ (total-70+80).toString()

        return binding.root
    }

    private fun totalPrice():Long{
        var totalPrice:Long = 0
        cartItemList.forEach { (key, value) -> totalPrice+=value.getItem().getItemPrice()*value.getItemQuantity()}
        return totalPrice
    }
}