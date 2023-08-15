package com.example.eatsy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity
import com.example.eatsy.databinding.ActivityRestaurantDetailBinding

class RestaurantDetail  : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}