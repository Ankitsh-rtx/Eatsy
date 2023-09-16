package com.example.eatsy.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.CardCartItemBinding
import com.example.eatsy.databinding.FragmentCartBinding
import com.example.eatsy.model.CartItem
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.log

class CartViewAdapter(private val context: Context? = null, private val cartListHM: HashMap<String,CartItem>,private  val view :FragmentCartBinding ) : RecyclerView.Adapter<CartViewAdapter.CartViewHolder>() {
    private lateinit var cartFragmentBinding:FragmentCartBinding
    class CartViewHolder (val binding: CardCartItemBinding):
        RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        cartFragmentBinding=FragmentCartBinding.bind(view.root)
        val binding = CardCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        var cartItems=DataSource.orderList.second
        val cartList:ArrayList<CartItem> = ArrayList(cartListHM.values)
        val item = cartList[position]

        holder.binding.cartItemName.text = item.getItem().name?.toTitleCase() ?: item.getItem().name
        holder.binding.cartItemPrice.text = "₹ " + (item.getItem().price
            ?.times(item.getItemQuantity())).toString()
        holder.binding.cartItemCount.text = item.getItemQuantity().toString()

        holder.binding.cartItemAddBtn.setOnClickListener {
            item.setItemQuantity(item.getItemQuantity()+1)
            item.getItem().id?.let { it1 -> cartListHM.put(it1, CartItem(item.getItem(),item.getItemQuantity())) }
            holder.binding.cartItemCount.text = (item.getItemQuantity()).toString()
            holder.binding.cartItemPrice.text = "₹ "+(item.getItemQuantity()* item.getItem().price!!).toString()
            setPrice(holder)
            storeData()
        }
        holder.binding.cartItemRemoveBtn.setOnClickListener {
            if (item.getItemQuantity() - 1 == 0) {
                cartListHM.remove(item.getItem().id)
                cartItems.remove(item.getItem().id)
                notifyItemRemoved(holder.bindingAdapterPosition)
            } else {
                item.setItemQuantity(item.getItemQuantity()-1)
                holder.binding.cartItemCount.text = (item.getItemQuantity()).toString()
                item.getItem().id?.let { it1 -> cartListHM.put(it1, CartItem(item.getItem(),item.getItemQuantity()) ) }
                holder.binding.cartItemPrice.text = "₹ "+(item.getItemQuantity()* item.getItem().price!!).toString()
            }
            setPrice(holder)
            storeData()

            if(cartListHM.size==0){
                cartFragmentBinding.emptyCart.visibility= View.VISIBLE;
                cartFragmentBinding.cartLayout.visibility=View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return cartListHM.size
    }
    private fun totalPrice():Long{
        var totalPrice:Long = 0
        var cartItemList:HashMap<String,CartItem> = DataSource.orderList.second
        cartItemList.forEach { (key, value) -> totalPrice+=(value.getItem().price
            ?.times(value.getItemQuantity())!!)}
        return totalPrice
    }
    private fun setPrice(holder: CartViewHolder) {
        var total:Long=totalPrice()
        cartFragmentBinding.totalPrice.text="₹ "+total.toString()
        cartFragmentBinding.payableAmount.text = "₹ "+ (total-70+80).toString()
        cartFragmentBinding.finalAmount.text = "₹ "+ (total-70+80).toString()
    }
    fun storeData(){
        val sharedPreferences = context?.getSharedPreferences("cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        Log.d("Menu Adapter", "cartList = ${cartListHM.size}")
        val jsonList = gson.toJson(cartListHM)
        editor?.putString("cartList",jsonList)
        editor?.apply()
    }
    private fun String.toTitleCase() = replaceFirstChar { text ->
        if (text.isLowerCase()) text.titlecase(Locale.getDefault())
        else text.toString()
    }

}