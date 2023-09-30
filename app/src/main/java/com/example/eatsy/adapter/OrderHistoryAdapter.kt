package com.example.eatsy.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.example.eatsy.databinding.CardOrderHistoryBinding

import com.example.eatsy.model.Order

class OrderHistoryAdapter(
    val orderList:MutableList<Order>)
    : Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {

    inner class OrderHistoryViewHolder(val binding:CardOrderHistoryBinding):ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val binding = CardOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHistoryViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val order = orderList[position]

        holder.binding.orderRestaurantNameTv.text = order.restaurantId.toString()
        holder.binding.orderRestaurantAddressTv.text = order.restaurantId.toString()
        holder.binding.orderPriceTv.text = "$"+order.totalPrice.toString()

        holder.binding.orderStatusTv.text = when(order.status){
            -1-> "Cancelled"
            0-> "Preparing"
            1-> "Dispatched"
            else-> "Delivered"
        }
        holder.binding.orderDetailsTv.text = order.items.toString()
        holder.binding.orderDateTv.text = order.timeZone?.toString()
    }
}