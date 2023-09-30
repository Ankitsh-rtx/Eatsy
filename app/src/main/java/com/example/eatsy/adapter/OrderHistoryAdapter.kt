package com.example.eatsy.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.eatsy.databinding.CardOrderHistoryBinding
import com.example.eatsy.model.Order

class OrderHistoryAdapter(
    val orderList:MutableList<Order>)
    : Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    inner class OrderHistoryViewHolder(val binding:CardOrderHistoryBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val binding = CardOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHistoryViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val order = orderList[position]

        holder.binding.orderRestaurantNameTv.text = (
                if (order.restaurantName?.isNotEmpty() == true)
                    order.restaurantName.toString()
                else order.restaurantId.toString()
                )
        holder.binding.orderRestaurantAddressTv.text = order.restaurantId.toString()
        holder.binding.orderPriceTv.text = "$"+order.totalPrice.toString()

        holder.binding.orderStatusTv.text = when(order.status){
            -1-> "Cancelled"
            0-> "Preparing"
            1-> "Dispatched"
            else-> "Delivered"
        }
        holder.binding.orderDetailsTv.text = getItemString(order.items)
        holder.binding.orderDateTv.text = order.timeZone?.toString()
    }
    fun getItemString(items:List<Any>?): String {
        val str = StringBuilder()

        items?.forEach { item ->

            val ite=item as HashMap<String,Any>
            Log.d("Noob", "getItemString: "+ ite["itemQuantity"].toString())
            val menu = (ite["item"] ) as HashMap<String,Any>
            val quantity = ite["itemQuantity"] as Long
            str.append("${menu["name"]}($quantity), ")
        }

        // Remove the trailing comma and space if there are items in the list
        if (str.isNotEmpty()) {
            str.deleteCharAt(str.length - 1) // Remove the trailing comma
            str.deleteCharAt(str.length - 1) // Remove the trailing space
        }

        return str.toString()
    }
}