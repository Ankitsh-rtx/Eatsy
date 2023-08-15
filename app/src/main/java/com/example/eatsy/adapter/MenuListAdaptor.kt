package com.example.eatsy.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.model.Item

class MenuListAdaptor (private val context: Context? = null) :
    RecyclerView.Adapter<MenuListAdaptor.MenuViewHolder>() {

    val items :List<Item> =DataSource.items
    class MenuViewHolder (private val view: View?): RecyclerView.ViewHolder(view!!) {
        val item_name:TextView=view!!.findViewById(R.id.item_name)
        val item_image:ImageView=view!!.findViewById(R.id.item_image)
        val item_price:TextView=view!!.findViewById(R.id.item_price)
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
        holder.item_name.text=item.getitemName()
        holder.item_price.text=item.getitemPrice().toString()
        holder.item_image.setImageResource(item.getitemsImage())
    }

    override fun getItemCount(): Int {
        return items.size
    }

}