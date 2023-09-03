package com.example.eatsy.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatsy.R
import com.example.eatsy.model.Restaurants
import com.example.eatsy.views.HomeFragment
import com.example.eatsy.views.RestaurantDetailsFragment

class RestaurantAdapter (private val context: Context? = null,
                         private val restaurants: MutableList<Restaurants>
                         ) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    // Initialize the data using the List found in data/DataSource
//    private val restaurants:List<Restaurants> = DataSource.restaurants

    /**
     * Initialize view elements
     */
    class RestaurantViewHolder(private val view: View?): RecyclerView.ViewHolder(view!!) {
        // Declare and initialize all of the list item UI components
        val restaurantImage: ImageView = view!!.findViewById(R.id.restaurant_image)
        val restaurantName: TextView = view!!.findViewById(R.id.restaurant_name)
        val restaurantType: TextView = view!!.findViewById(R.id.restaurant_type)
        val restaurantRating:TextView = view!!.findViewById(R.id.restaurant_rating_text)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        // Use a conditional to determine the layout type and set it accordingly.
        //  if the layout variable is Layout.GRID the grid list item should be used. Otherwise the
        //  the vertical/horizontal list item should be used.

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_top_restaurants, parent, false)
        // Null should not be passed into the view holder. This should be updated to reflect
        //  the inflated layout.
        return RestaurantViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = restaurants.size// return the size of the data set instead of 0

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        // Get the data at the current position
        val item = restaurants[position]
        //  Set the image resource for the current restaurant
        //  Set the text for the current restaurant's name
        //  Set the text for the current restaurant's type
        if (context != null) {
            Glide.with(context).load(item.image).into(holder.restaurantImage)
        };

//        holder.restaurantImage.setImageResource(item.imageResourceId)
        holder.restaurantName.text = item.getRestaurantName()
        holder.restaurantType.text = item.getRestaurantType()
        holder.restaurantRating.text = item.getRestaurantRating().toString()

        holder.itemView.setOnClickListener {

//            val cartFragment = CartFragment()
//            val args = Bundle()
//            args.putString("restaurant_name",item.getRestaurantName())
//            args.putString("restaurant_type",item.getRestaurantType())
//            args.putString("restaurant_rating",item.getRestaurantRating().toString())
//            args.putString("restaurant_address",item.getRestaurantAddress())
//            args.putSerializable("restaurant_menu", item.getMenu())
//            cartFragment.arguments = args
//            val fragmentManager =
//            cartFragment.fragmentManager?.beginTransaction()?.add(R.id.homeFragment, cartFragment)
//                ?.commit()


//            context?.startActivity(
//                Intent(context, RestaurantDetail::class.java)
//                    .putExtra("restaurant",item)
//
//            )
            val args = Bundle()
            args.putSerializable("res",item)
            val newFragment = RestaurantDetailsFragment()
            newFragment.arguments = args
            Log.d("Restaurant Adapter", "onBindViewHolder: ${item.name}")

            holder.itemView.findFragment<HomeFragment>().activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, newFragment)?.addToBackStack(R.id.homeFragment.toString())?.commit()
        }



    }
}


