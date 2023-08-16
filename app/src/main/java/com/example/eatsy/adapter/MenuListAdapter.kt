package com.example.eatsy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource

import com.example.eatsy.databinding.ItemLayoutBinding
import com.example.eatsy.model.Item

class MenuListAdapter (private val context: Context? = null) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(view:View, position: Int)
        fun onItemAddClick(view:View, position: Int)
        fun onItemRemoveClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    val item:List<Item> =DataSource.items

    class MenuViewHolder (val binding: ItemLayoutBinding, listener: OnItemClickListener):
        RecyclerView.ViewHolder(binding.root){

        //constructor
        init{
            binding.itemAddButton.setOnClickListener {
                listener.onItemClick(itemView,adapterPosition)
            }
            binding.itemAddBtn.setOnClickListener {
                listener.onItemAddClick(itemView,adapterPosition)
            }
            binding.itemRemoveBtn.setOnClickListener {
                listener.onItemRemoveClick(itemView,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding,mListener)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item:Item = DataSource.items[position]
        holder.binding.itemName.text= item.getItemName()
        holder.binding.itemPrice.text= "₹ "+item.getItemPrice().toString()
        holder.binding.itemImage.setImageResource(item.imageResourceId)

    }

    override fun getItemCount(): Int {
        return DataSource.items.size
    }

}