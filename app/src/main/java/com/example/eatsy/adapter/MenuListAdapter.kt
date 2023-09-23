package com.example.eatsy.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.CardMenuItemBinding
import com.example.eatsy.databinding.FragmentRestaurantDetailsBinding
import com.example.eatsy.model.CartItem
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.example.eatsy.views.CartFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.util.*



class MenuListAdapter (
    private val context: Context,
    val item: MutableList<Item>,
    // menu item received from the restaurant details activity
//    val view :FragmentRestaurantDetailBinding
    val view: FragmentRestaurantDetailsBinding,
    val res: Restaurants?
) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {

     private  lateinit var v: FragmentRestaurantDetailsBinding
     val cartItemList = DataSource.orderList.second
    private var cart = DataSource.orderList
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

    class MenuViewHolder (val binding: CardMenuItemBinding):
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
        v= FragmentRestaurantDetailsBinding.bind(view.root)

        val binding = CardMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        // arraylist that is passed through restaurant detail activity is used here
        val items:Item = item[position]
        var value=1
        Log.d("check", "onBindViewHolder: called")

        // Dialog Box Appears when we choose items from different restaurant
        val replaceDialog =Dialog(context)
        replaceDialog.setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_replace_cart_item,null,false))
        replaceDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val replaceText = replaceDialog.findViewById<TextView>(R.id.dialog_text)
        // replace button working
        val replace =replaceDialog.findViewById<TextView>(R.id.replace)
        replace.setOnClickListener{
            cartItemList.clear()
            DataSource.orderList= Pair(res,cartItemList)
            addItem(holder,items,value)
            replaceDialog.dismiss()
        }
        // cancel button working
        val cancel = replaceDialog.findViewById<TextView>(R.id.cancel)
        cancel.setOnClickListener{
            replaceDialog.dismiss()
            return@setOnClickListener
        }

        if(DataSource.orderList.first==res) {
            if (cartItemList.containsKey(items.id)) {
                Log.d("help", "onBindViewHolder: value ")
                value = cartItemList.getValue(items.id.toString()).getItemQuantity()
                holder.binding.itemAddButton.text = (value).toString()
                holder.binding.itemAddBtn.visibility = View.VISIBLE
                holder.binding.itemRemoveBtn.visibility = View.VISIBLE
            }
            else {
                holder.binding.itemAddButton.text = "ADD"
                holder.binding.itemAddBtn.visibility = View.INVISIBLE
                holder.binding.itemRemoveBtn.visibility = View.INVISIBLE
            }
        }
        holder.binding.itemName.text= items.name?.toTitleCase() ?: items.name
        holder.binding.itemPrice.text= "₹ "+items.price.toString()
        Glide.with(context).load(items.image).into(holder.binding.itemImage);
        if(items.isVeg==false){
            holder.binding.itemVeg.setImageResource(R.drawable.ic_non_veg)
            context.let { ContextCompat.getColor(it,R.color.red_500) }
                .let { holder.binding.itemVeg.drawable.setTint(it) }
        }
        else if(items.isVeg==true) {
            holder.binding.itemVeg.setImageResource(R.drawable.ic_veg)
            context.let { ContextCompat.getColor(it,R.color.green_700) }
                .let { holder.binding.itemVeg.drawable.setTint(it) }
        }
        if(cartItemList.size!=0){

            v.itemCount.text = cartItemList.size.toString()+" Items"
            v.price.text = "₹"+totalPrice().toString()
        }
        else {
            DataSource.orderList = Pair(null,cartItemList)
        }

        holder.binding.itemAddButton.setOnClickListener {
            if( DataSource.orderList.first==null) {
                cartItemList.clear()
                DataSource.orderList= Pair(res,cartItemList)
                addItem(holder,items,value)
            }
            else if( DataSource.orderList.first!=res) {
                val str = replaceItemString(DataSource.orderList.first,res)
                replaceText.text = str
                replaceDialog.show()

            }
            else {
                addItem(holder,items,value)
            }
            storeData()

        }

        holder.binding.itemAddBtn.setOnClickListener {
            cartItemList.put(items.id.toString(), CartItem(items,value+1))
            if(cartItemList.size!=0) {
                v.goToCartDialog.visibility= View.VISIBLE
                v.price.text = "₹"+totalPrice().toString()
            }
            else v.goToCartDialog.visibility= View.GONE
            holder.binding.itemAddButton.text = (++value).toString()
            storeData()

        }

        holder.binding.itemRemoveBtn.setOnClickListener {

            if (value <= 1) {
                holder.binding.itemAddButton.text = "ADD"
                holder.binding.itemAddBtn.visibility = View.INVISIBLE
                holder.binding.itemRemoveBtn.visibility = View.INVISIBLE
                cartItemList.remove(items.id)
            } else{
                v.itemCount.text = cartItemList.size.toString()+ " Item"
                v.price.text = "₹"+totalPrice().toString()
                holder.binding.itemAddButton.text = (--value).toString()
                cartItemList.put(items.id.toString(),CartItem(items,value))
            }
            if(cartItemList.size!=0) {
                v.goToCartDialog.visibility= View.VISIBLE
                v.itemCount.text = cartItemList.size.toString()+ " Item"
                v.price.text = "₹"+totalPrice().toString()

            }
            else v.goToCartDialog.visibility= View.GONE
            storeData()

        }
        // get item details page
        holder.binding.itemDetailsLayout.setOnClickListener {
            val dialog = BottomSheetDialog(context)
            val bottomSheetDialog =
                LayoutInflater.from(context).inflate(R.layout.bottom_detail_dialog, null)


            dialog.setContentView(bottomSheetDialog)
            val image = dialog.findViewById<ImageView>(R.id.itemImage)
            if (image != null) {
                Glide.with(context).load(items.image).into(image)
            }
            val title = dialog.findViewById<TextView>(R.id.titleText)
            title?.text = items.name?.toTitleCase()
            val price = dialog.findViewById<TextView>(R.id.itemPrice)
            price?.text = "₹ "+items.price.toString()
            val description = dialog.findViewById<TextView>(R.id.itemDescription)
            description?.text = items.description?.toTitleCase()
            val vegIcon = dialog.findViewById<ImageView>(R.id.vegIcon)
            if(items.isVeg==true) {
                vegIcon?.setImageResource(R.drawable.ic_veg)
                vegIcon?.setColorFilter(ContextCompat.getColor(context, R.color.green_700), android.graphics.PorterDuff.Mode.SRC_IN)
            }else {
                vegIcon?.setImageResource(R.drawable.ic_non_veg)
                vegIcon?.setColorFilter(ContextCompat.getColor(context, R.color.red_500), android.graphics.PorterDuff.Mode.SRC_IN)
            }


            dialog.show()
        }

        val bundle = Bundle()
        bundle.putSerializable("cartItems",cartItemList)
        val cartFragment = CartFragment()
        cartFragment.arguments = bundle

    }
    private fun storeData(){
        val sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        Log.d("Menu Adapter", "cartList = ${cartItemList.size}")
        val jsonList = gson.toJson(cartItemList)
        val jsonRes = gson.toJson(res)

        editor?.putString("res",jsonRes)
        editor?.putString("cartList",jsonList)
        editor?.apply()
    }

    override fun getItemCount(): Int {
        // return the size of the current menu list
        return item.size
    }

    private fun totalPrice():Long{
        var totalPrice:Long = 0
        val cartItemList:HashMap<String,CartItem> = DataSource.orderList.second
        cartItemList.forEach { (key, value) -> totalPrice+=(value.getItem().price
            ?.times(value.getItemQuantity())!!)}
        return totalPrice
    }

    private fun String.toTitleCase() = replaceFirstChar { text ->
        if (text.isLowerCase()) text.titlecase(Locale.getDefault())
        else text.toString()
    }

    private fun addItem(holder :MenuViewHolder,items:Item ,value:Int){
//        notifyDataSetChanged()
        if (holder.binding.itemAddBtn.visibility == View.INVISIBLE && holder.binding.itemRemoveBtn.visibility == View.INVISIBLE) {
            holder.binding.itemAddBtn.visibility = View.VISIBLE
            holder.binding.itemRemoveBtn.visibility = View.VISIBLE
        }
        cartItemList.put(items.id.toString(), CartItem(items, value))
        if(cartItemList.size!=0) {
            v.goToCartDialog.visibility= View.VISIBLE
            v.itemCount.text = cartItemList.size.toString()+ " Items"
            v.price.text = "₹"+totalPrice().toString()
        }
        else v.goToCartDialog.visibility= View.GONE
        holder.binding.itemAddButton.text = (value).toString()

    }

    private fun replaceItemString(res: Restaurants?, replace_res: Restaurants?): String {
        return "Your cart contains dishes from ${res?.name}." +
                " Do you want to discard the selections and add new dishes from ${replace_res?.name}?"
    }
}
