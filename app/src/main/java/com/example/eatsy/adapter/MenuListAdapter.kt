package com.example.eatsy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource

import com.example.eatsy.databinding.ItemLayoutBinding
import com.example.eatsy.model.Item

class MenuListAdapter (
    private val context: Context? = null,
    val item: ArrayList<Item> // menu item received from the restaurant details activity
) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {

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
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        // arraylist that is passed through restaurant detail activity is used here
        val items:Item = item[position]
        var value=1
        holder.binding.itemName.text= items.getItemName()
        holder.binding.itemPrice.text= "₹ "+items.getItemPrice().toString()
        holder.binding.itemImage.setImageResource(items.imageResourceId)
        holder.binding.itemAddButton.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {

                if(holder.binding.itemAddBtn.visibility == View.INVISIBLE && holder.binding.itemRemoveBtn.visibility == View.INVISIBLE) {
                    holder.binding.itemAddBtn.visibility = View.VISIBLE
                    holder.binding.itemRemoveBtn.visibility = View.VISIBLE
                }
                holder.binding.itemAddButton.text = (value).toString()
            }
        })
        holder.binding.itemAddBtn.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {

                holder.binding.itemAddButton.text = (++value).toString()
            }
        })
        holder.binding.itemRemoveBtn.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(value<=1){
                    holder.binding.itemAddButton.text = "ADD"
                    holder.binding.itemAddBtn.visibility = View.INVISIBLE
                    holder.binding.itemRemoveBtn.visibility = View.INVISIBLE
                }
                else holder.binding.itemAddButton.text = (--value).toString()
            }
        })


    }

    override fun getItemCount(): Int {
        // return the size of the current menu list
        return item.size
    }

}