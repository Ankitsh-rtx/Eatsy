package com.example.eatsy.adapter

import com.example.eatsy.model.Dishes
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.R

class TopDishAdapter (private val context: Context? = null) :
    RecyclerView.Adapter<TopDishAdapter.DishCardViewHolder>() {

        // Initialize the data using the List found in data/DataSource
        private val dishes:List<Dishes> = DataSource.dish

        /**
         * Initialize view elements
         */
        class DishCardViewHolder(private val view: View?): RecyclerView.ViewHolder(view!!) {
            // Declare and initialize all of the list item UI components
            val imageView: ImageView = view!!.findViewById(R.id.dish_icon)
            val dishName: TextView = view!!.findViewById(R.id.dish_text)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishCardViewHolder {
            // Use a conditional to determine the layout type and set it accordingly.
            //  if the layout variable is Layout.GRID the grid list item should be used. Otherwise the
            //  the vertical/horizontal list item should be used.

            val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_top_dishes, parent, false)
            // Null should not be passed into the view holder. This should be updated to reflect
            //  the inflated layout.
            return DishCardViewHolder(adapterLayout)
        }

        override fun getItemCount(): Int = dishes.size// return the size of the data set instead of 0

        override fun onBindViewHolder(holder: DishCardViewHolder, position: Int) {
            // Get the data at the current position
            val item = dishes[position]
            //  Set the image resource for the current dog
            //  Set the text for the current dog's name
            //  Set the text for the current dog's age
            holder.imageView.setImageResource(item.imageResourceId)
            holder.dishName.text = item.getDishName()

            //  Set the text for the current dog's hobbies by passing the hobbies to the
        }
    }
