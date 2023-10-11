package com.example.eatsy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatsy.databinding.CardSearchRestaurantBinding
import com.example.eatsy.model.Restaurants

class SearchRestaurantAdaptor(private val context: Context, private val itemsList: List<Restaurants>):
    RecyclerView.Adapter<SearchRestaurantAdaptor.SearchViewHolder>() {
    inner class SearchViewHolder(val binding:CardSearchRestaurantBinding)
        :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = CardSearchRestaurantBinding.inflate(LayoutInflater.from(context),parent,false)
        return SearchViewHolder (binding)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = itemsList[position]

        holder.binding.searchRestaurantName.text = item.name
        Glide.with(context).load(item.image).into(holder.binding.searchRestaurantImage)
        holder.binding.searchRating.text=item.rating.toString()
    }

}