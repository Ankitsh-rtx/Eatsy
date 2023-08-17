package com.example.eatsy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.databinding.CartviewItemLayoutBinding
import com.example.eatsy.model.CartItem
import com.example.eatsy.model.Item

class CartViewAdapter(private val context: Context? = null) : RecyclerView.Adapter<CartViewAdapter.CartViewHolder>() {


    val orders: List<CartItem> = DataSource.orders

    class CartViewHolder (val binding: CartviewItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = CartviewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item: CartItem = DataSource.orders[position]
        var value=1
        holder.binding.cartItemName.text= item.getItemName()
        holder.binding.cartItemPrice.text= "₹ "+item.getItemPrice().toString()
        holder.binding.cartItemCount.text = item.getItemQuantity().toString()

        holder.binding.cartItemAddBtn.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {

                holder.binding.cartItemCount.text = (++value).toString()
            }
        })
        holder.binding.cartItemRemoveBtn.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                holder.binding.cartItemCount.text = (--value).toString()
            }
        })


    }

    override fun getItemCount(): Int {
        return DataSource.orders.size
    }

}