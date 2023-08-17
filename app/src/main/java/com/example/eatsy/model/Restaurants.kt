package com.example.eatsy.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Restaurants(
    val id: String,
    @DrawableRes val imageResourceId: Int,
    val name: String,
    val type: String,
    val rating: Double,
    val address: String,
    val menuItemList: ArrayList<Item> = ArrayList<Item>()

    ): Serializable {

    fun getRestaurantId(): String {
        return id
    }
    fun getRestaurantName(): String {
        return name
    }
    fun getRestaurantType(): String {
        return type
    }
    fun getRestaurantRating():Double{
        return rating
    }
    fun getRestaurantAddress(): String {
        return address
    }
    fun getMenu() : ArrayList<Item>{
        return menuItemList
    }





}