package com.example.eatsy.model

import android.util.Log
import com.example.eatsy.DataSource
import java.sql.Timestamp
import java.util.Date

data class Order(
    val userId: String?="",
    val restaurantId:String?="",
    val restaurantName:String?="",
    val items: List<Any> ?= null,
    val timeZone: Date? = null,
    val totalPrice: Int?=null,
    val status:Int?=null,
    val address: Address? = null,
    val paymentMode:Int?=0,
    val transactionId:String?=""
){
}
