package com.example.eatsy.model

import androidx.annotation.DrawableRes
import java.io.Serializable
import java.net.URL

data class Restaurants@JvmOverloads constructor(
    val id: String = "",
    val image: String? = null,
    val name: String = "",
    val type: String = "",
    val rating: Double = 0.0,
    val city: String = "",
    val country: String = "",
    val contact: String = "",
    val pincode: Int = 0,
    val street: String = "",
    val isActive: Boolean = false,
    val isOpen: Boolean = false,
    var menuItemList: ArrayList<Item>? = ArrayList()

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

    fun getMenu() : ArrayList<Item>? {
        return menuItemList
    }
}