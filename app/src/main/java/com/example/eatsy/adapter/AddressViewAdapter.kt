package com.example.eatsy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.model.Address
import com.example.eatsy.databinding.AddressViewBinding

class AddressViewAdapter(private val context: Context? = null , private val address: MutableList<Address>?= mutableListOf() )  : RecyclerView.Adapter<AddressViewAdapter.AddressViewHolder>()  {
     private lateinit var onItemClickListener :OnItemClickListener
    class AddressViewHolder (val binding: AddressViewBinding,
    ):
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

        val address = address!!.get(position)
        holder.binding.address.text=address.toString()
        holder.itemView.setOnClickListener{
            if(onItemClickListener!=null) {
                onItemClickListener!!.onClick(position,address)
            }
        }
    }

    fun  setOnClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener=onItemClickListener
    }
    override fun getItemCount(): Int {
        return address?.size ?: 0
    }
    interface OnItemClickListener{
        fun onClick(position: Int,address:Address)
    }
}