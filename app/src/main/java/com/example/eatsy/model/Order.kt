package com.example.eatsy.model

import com.google.type.DateTime
import java.sql.Timestamp
import java.util.SimpleTimeZone

data class Order(
    val userId: String?="",
    val restaurantId:String?="",
    val items: List<Any> ?= null,
    val timeZone: Timestamp? = null,
    val totalPrice: Int?=null,
    val status:Int?=null,
    val address: Address? = null,
    val paymentMode:Int?=0,
    val transactionId:String?=""
)
