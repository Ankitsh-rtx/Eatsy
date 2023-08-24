package com.example.eatsy.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.ActivityRestaurantDetailBinding
import com.example.eatsy.databinding.ItemLayoutBinding
import com.example.eatsy.model.CartItem
import com.example.eatsy.model.Item
import com.example.eatsy.views.CartFragment
import com.example.eatsy.views.RestaurantDetail


class MenuListAdapter (
    private val context: Context? = null,
    val item: ArrayList<Item>,
    // menu item received from the restaurant details activity
    val view :ActivityRestaurantDetailBinding
) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {

     private  lateinit var v:ActivityRestaurantDetailBinding
     val cartItemList = DataSource.orderList
//    private lateinit var mListener: OnItemClickListener

//    interface OnItemClickListener{
//        fun onItemClick(view:View, position: Int)
//        fun onItemAddClick(view:View, position: Int)
//        fun onItemRemoveClick(view: View, position: Int)
//    }
//
//    fun setOnItemClickListener(listener: OnItemClickListener){
//        mListener = listener
//    }

    class MenuViewHolder (val binding: ItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){


        //constructor
//        init{
//            binding.itemAddButton.setOnClickListener {
//                listener.onItemClick(itemView,adapterPosition)
//            }
//            binding.itemAddBtn.setOnClickListener {
//                listener.onItemAddClick(itemView,adapterPosition)
//            }
//            binding.itemRemoveBtn.setOnClickListener {
//                listener.onItemRemoveClick(itemView,adapterPosition)
//            }
//        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        v= ActivityRestaurantDetailBinding.bind(view.root)
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        // arraylist that is passed through restaurant detail activity is used here
        val items:Item = item[position]
        var value=1
        if(cartItemList.containsKey(items.id)) {
            value=cartItemList.getValue(items.id).getItemQuantity()
            holder.binding.itemAddButton.text = (value).toString()
            holder.binding.itemAddBtn.visibility = View.VISIBLE
            holder.binding.itemRemoveBtn.visibility = View.VISIBLE

        }
        holder.binding.itemName.text= items.getItemName()
        holder.binding.itemPrice.text= "₹ "+items.getItemPrice().toString()
        holder.binding.itemImage.setImageResource(items.imageResourceId)
        holder.binding.itemAddButton.setOnClickListener {
            if (holder.binding.itemAddBtn.visibility == View.INVISIBLE && holder.binding.itemRemoveBtn.visibility == View.INVISIBLE) {
                holder.binding.itemAddBtn.visibility = View.VISIBLE
                holder.binding.itemRemoveBtn.visibility = View.VISIBLE
            }
            cartItemList.put(items.id, CartItem(items, value))
            if(cartItemList.size!=0) {
                v.goToCartDialog.visibility= View.VISIBLE
            }
            else v.goToCartDialog.visibility= View.GONE
            holder.binding.itemAddButton.text = (value).toString()
        }

        holder.binding.itemAddBtn.setOnClickListener {

            cartItemList.put(items.id, CartItem(items,value+1))
            if(cartItemList.size!=0) {
                v.goToCartDialog.visibility= View.VISIBLE
            }
            else v.goToCartDialog.visibility= View.GONE
            holder.binding.itemAddButton.text = (++value).toString()

        }

        holder.binding.itemRemoveBtn.setOnClickListener {
            if (value <= 1) {
                holder.binding.itemAddButton.text = "ADD"
                holder.binding.itemAddBtn.visibility = View.INVISIBLE
                holder.binding.itemRemoveBtn.visibility = View.INVISIBLE
                cartItemList.remove(items.id)


            } else{
                holder.binding.itemAddButton.text = (--value).toString()
                cartItemList.put(items.id,CartItem(items,value))
            }
            if(cartItemList.size!=0) {
                v.goToCartDialog.visibility= View.VISIBLE
            }
            else v.goToCartDialog.visibility= View.GONE

        }

        val bundle = Bundle()
        bundle.putSerializable("cartItems",cartItemList)
        val cartFragment = CartFragment()
        cartFragment.arguments = bundle


    }

    override fun getItemCount(): Int {
        // return the size of the current menu list
        return item.size
    }


}
