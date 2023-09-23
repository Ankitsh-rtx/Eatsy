package com.example.eatsy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatsy.R
import com.example.eatsy.databinding.CardSearchItemChildBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants

class SearchAdapterChildItems(private val context: Context, private val itemsList: MutableList<Item>):
    RecyclerView.Adapter<SearchAdapterChildItems.SearchChildItemViewHolder>() {
    inner class SearchChildItemViewHolder(val binding:CardSearchItemChildBinding)
        :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchChildItemViewHolder {
        val binding = CardSearchItemChildBinding.inflate(LayoutInflater.from(context),parent,false)
        return SearchChildItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: SearchChildItemViewHolder, position: Int) {
        val item = itemsList[position]

        holder.binding.searchItemName.text = item.name
        holder.binding.searchItemPrice.text = "₹"+item.price.toString()
        if(item.isVeg == true) {
            holder.binding.searchIconVeg.setImageResource(R.drawable.ic_veg)
            context.let { ContextCompat.getColor(it,R.color.green_700) }
                .let {
                    holder.binding.searchIconVeg.drawable.setTint(it)
                }
        }
        else {
            holder.binding.searchIconVeg.setImageResource(R.drawable.ic_non_veg)
            context.let { ContextCompat.getColor(it,R.color.red_500) }
            .let {
                holder.binding.searchIconVeg.drawable.setTint(it)
            }
        }
        Glide.with(context).load(item.image).into(holder.binding.searchItemImage)
    }

}