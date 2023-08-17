package com.example.eatsy.model

import androidx.annotation.DrawableRes

class CartItem(
    val name: String,
    private val quantity: Int,
    private val price: Int

) {
    fun getItemName(): String {
        return name
    }

    fun getItemPrice(): Int {
        return price
    }

    fun getItemQuantity(): Int {
        return quantity
    }
}