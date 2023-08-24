package com.example.eatsy.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.databinding.CartviewItemLayoutBinding
import com.example.eatsy.databinding.FragmentCartBinding
import com.example.eatsy.model.CartItem

class CartViewAdapter(private val context: Context? = null, private val cartListHM: HashMap<String,CartItem>,private  val view :FragmentCartBinding ) : RecyclerView.Adapter<CartViewAdapter.CartViewHolder>() {

    class CartViewHolder (val binding: CartviewItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = CartviewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val cartList:ArrayList<CartItem> = ArrayList(cartListHM.values)
        val item = cartList[position]

        holder.binding.cartItemName.text = item.getItem().getItemName()
        holder.binding.cartItemPrice.text = "₹ " + (item.getItem().getItemPrice()*item.getItemQuantity()).toString()
        holder.binding.cartItemCount.text = item.getItemQuantity().toString()

        holder.binding.cartItemAddBtn.setOnClickListener {
            item.setItemQuantity(item.getItemQuantity()+1)
            cartListHM.put( item.getItem().id, CartItem(item.getItem(),item.getItemQuantity()))
            holder.binding.cartItemCount.text = (item.getItemQuantity()).toString()
            holder.binding.cartItemPrice.text = "₹ "+(item.getItemQuantity()*item.getItem().getItemPrice()).toString()
            setPrice()
        }
        holder.binding.cartItemRemoveBtn.setOnClickListener {
            if (item.getItemQuantity() - 1 == 0) {
                cartListHM.remove(item.getItem().id)
                notifyItemRemoved(holder.bindingAdapterPosition)
            } else {

                item.setItemQuantity(item.getItemQuantity()-1)
                holder.binding.cartItemCount.text = (item.getItemQuantity()).toString()
                cartListHM.put( item.getItem().id, CartItem(item.getItem(),item.getItemQuantity()) )
                holder.binding.cartItemPrice.text = "₹ "+(item.getItemQuantity()*item.getItem().getItemPrice()).toString()
            }
            setPrice()
        }

    }

    override fun getItemCount(): Int {
        return cartListHM.size
    }
    private fun totalPrice():Long{
        var totalPrice:Long = 0
        var cartItemList:HashMap<String,CartItem>
        cartItemList= DataSource.orderList
        cartItemList.forEach { (key, value) -> totalPrice+=value.getItem().getItemPrice()}
        return totalPrice
    }
    private fun setPrice() {
        Log.d("G", "setPrice")
        view.totalPrice.text = "₹ "+totalPrice().toString()
        view.payableAmount.text = "₹ "+ (totalPrice()-70+80).toString()
        view.finalAmount.text = "₹ "+ (totalPrice()-70+80).toString()
    }
}