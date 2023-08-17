package com.example.eatsy;


import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log

import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.adapter.MenuListAdapter
import com.example.eatsy.databinding.ActivityRestaurantDetailBinding


class RestaurantDetail  : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent:Intent = intent
        val name = intent.getStringExtra("restaurant_name")
        val type = intent.getStringExtra("restaurant_type")

        binding.restaurantNameTextview.text = name
        binding.restaurantType.text = type

        // Status bar color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.off_white)

        val adapter = MenuListAdapter(this)
        binding.menuItemRecyclerview.adapter= adapter
        binding.menuItemRecyclerview.layoutManager = LinearLayoutManager(this)
        // Specify fixed size to improve performance

        binding.menuItemRecyclerview.setHasFixedSize(true)
        binding.menuItemRecyclerview.isNestedScrollingEnabled = false
        val countArr = IntArray(DataSource.items.size) { i-> 1 }



    }


}