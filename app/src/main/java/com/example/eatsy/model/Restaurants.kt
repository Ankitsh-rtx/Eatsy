package com.example.eatsy.model
import androidx.annotation.DrawableRes
data class Restaurants(
    @DrawableRes val imageResourceId: Int,
    val name: String,
    val type: String

    ) {
    fun getRestaurantName(): String {
        return name
    }
    fun getRestaurantType():String{
        return type
    }

}