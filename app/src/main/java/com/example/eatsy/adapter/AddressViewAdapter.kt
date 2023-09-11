package com.example.eatsy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.model.Address
import com.example.eatsy.databinding.AddressViewBinding

class AddressViewAdapter(private val context: Context? = null)  : RecyclerView.Adapter<AddressViewAdapter.AddressViewHolder>()  {
    private val address=DataSource.address
    class AddressViewHolder (val binding: AddressViewBinding):
        RecyclerView.ViewHolder(binding.root){
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {
        val binding = AddressViewBinding.inflate(LayoutInflater.from(context),parent,false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewAdapter.AddressViewHolder, position: Int) {

        val address = address[position]
        holder.binding.address.text=address.toString()
    }

    override fun getItemCount(): Int {
        return address.size
    }
}