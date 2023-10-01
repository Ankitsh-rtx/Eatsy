package com.example.eatsy.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable.Orientation
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatsy.databinding.AddressViewBinding
import com.example.eatsy.databinding.CardSearchItemBinding
import com.example.eatsy.model.Address
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants

class SearchAdapter(private val context: Context, private val itemMap: HashMap<Restaurants,MutableList<Item>> )
    :RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener
    private val restaurantList: MutableList<Restaurants> = itemMap.keys.toMutableList()

    class SearchViewHolder(val binding: CardSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = CardSearchItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemMap.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val itemsList = itemMap.getValue(restaurantList[position])
        val restaurant = restaurantList[position]
        holder.binding.searchRestaurantName.text = restaurant.name
        holder.binding.searchRating.text = restaurant.rating.toString()
        holder.binding.searchRecyclerview.setHasFixedSize(true)
        holder.binding.searchRecyclerview.layoutManager =
            StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL)
        val adapter = SearchAdapterChildItems(context, itemsList)
        holder.binding.searchRecyclerview.adapter = adapter
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onClick(restaurant)
            }
        }

    }
    fun  setOnClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener=onItemClickListener
}

    interface OnItemClickListener{
        fun onClick(restaurants: Restaurants)
    }

}
