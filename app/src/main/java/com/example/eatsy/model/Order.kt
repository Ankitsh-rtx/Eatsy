package com.example.eatsy.model

import android.location.Address
import java.util.SimpleTimeZone

data class Order(
    val userId: String?="",
    val restaurantId:String?="",
    val items: ArrayList<Item>? = null,
    val timeZone: SimpleTimeZone? = null,
    val totalPrice: Int?=null,
    val status:Int?=null,
    val address: Address? = null,
    val paymentMode:Int?=0
)
