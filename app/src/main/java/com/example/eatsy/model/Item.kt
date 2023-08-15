package com.example.eatsy.model

import androidx.annotation.DrawableRes

class Item (
        @DrawableRes val imageResourceId: Int,
        val name: String,
        private val type: String,
        private val price: Int
        ){
        fun getItemName():String{
                return name
        }
        fun getItemPrice():Int{
                return price
        }
        fun getItemType(): String{
                return type
        }
}