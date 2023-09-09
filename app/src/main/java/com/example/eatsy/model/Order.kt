package com.example.eatsy.model

import java.util.SimpleTimeZone

data class Order(
    val userId: String?="",
    val restaurantId:String?="",
    val items: List<Any> ?= null,
    val timeZone: SimpleTimeZone? = null,
    val totalPrice: Int?=null,
    val status:Int?=null,
    val address: Address? = null,
    val paymentMode:Int?=0,
    val transactionId:String?=""
)
