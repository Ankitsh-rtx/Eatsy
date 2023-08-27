package com.example.eatsy.views;


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener

import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.MenuListAdapter
import com.example.eatsy.databinding.ActivityRestaurantDetailBinding
import com.example.eatsy.model.CartItem
import com.example.eatsy.model.Item


class RestaurantDetail  : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailBinding
    private lateinit var cartItemList:HashMap<String, CartItem>
    private lateinit var adapter: MenuListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent:Intent = intent
        val name = intent.getStringExtra("restaurant_name")
        val type = intent.getStringExtra("restaurant_type")
        val rating = intent.getStringExtra("restaurant_rating")
        val address = intent.getStringExtra("restaurant_address")
        val menu= (intent.getSerializableExtra("restaurant_menu"))as ArrayList<Item>

        binding.restaurantNameTextview.text = name
        binding.restaurantType.text = type
        binding.restaurantRating.text = rating
        binding.restaurantLocation.text = address

        // Status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.ash_white)

        adapter = MenuListAdapter(this, menu,binding)
        binding.menuItemRecyclerview.adapter= adapter
        binding.menuItemRecyclerview.layoutManager = LinearLayoutManager(this)

        binding.goToCartDialog.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            i.putExtra("cart", "cart")
            startActivity(i)
        }
        // Specify fixed size to improve performance
        binding.menuItemRecyclerview.setHasFixedSize(true)
        binding.menuItemRecyclerview.isNestedScrollingEnabled = false
        val countArr = IntArray(DataSource.items.size) { i-> 1 }

        cartItemList=DataSource.orderList
        if(cartItemList.size!=0) {
            binding.goToCartDialog.visibility= View.VISIBLE
        }
    }
    override fun onResume(){
        super.onResume()
        adapter.notifyDataSetChanged()
        binding.menuItemRecyclerview.adapter = adapter
        Log.d("g", cartItemList.toString())
        cartItemList=DataSource.orderList
        if(cartItemList.size==0) {
            binding.goToCartDialog.visibility= View.GONE
        }
    }
    // function to override large font size into normal font size
    override fun attachBaseContext(newBase: Context?) {

        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }


}