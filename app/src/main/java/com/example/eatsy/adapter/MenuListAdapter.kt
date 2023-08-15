package com.example.eatsy.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.DataSource.items
import com.example.eatsy.R
import com.example.eatsy.model.Item

class MenuListAdapter (private val context: Context? = null) :
    RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {

    val item:List<Item> =DataSource.items
    class MenuViewHolder (private val view: View?): RecyclerView.ViewHolder(view!!) {
        val itemName:TextView=view!!.findViewById(R.id.item_name)
        val itemImage:ImageView=view!!.findViewById(R.id.item_image)
        val itemPrice:TextView=view!!.findViewById(R.id.item_price)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MenuViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item:Item =items[position]
        holder.itemName.text= item.getItemName()
        holder.itemPrice.text= "₹ "+item.getItemPrice().toString()
        holder.itemImage.setImageResource(item.imageResourceId)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}