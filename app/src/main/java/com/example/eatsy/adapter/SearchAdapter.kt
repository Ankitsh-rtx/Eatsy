package com.example.eatsy.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eatsy.R
import com.example.eatsy.databinding.CardSearchItemBinding
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.example.eatsy.views.MainActivity
import com.example.eatsy.views.RestaurantDetailsFragment
import com.example.eatsy.views.SearchFragment

class SearchAdapter(private val context: Context, private val itemMap: HashMap<Restaurants,MutableList<Item>>, val activity:FragmentActivity )
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
        val adapter = SearchAdapterChildItems(context, itemsList, restaurant)
        adapter.setOnClickListener(object : SearchAdapterChildItems.OnItemClickListener{
            override fun onItemClick(restaurants: Restaurants) {
                val args = Bundle()
                args.putSerializable("res",restaurants)
                val newFragment = RestaurantDetailsFragment()
                newFragment.arguments = args
                // Log.d("Restaurant Adapter", "onBindViewHolder: ${item.name}")

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, newFragment).addToBackStack(null)
                    .commit()
            }
        })

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
