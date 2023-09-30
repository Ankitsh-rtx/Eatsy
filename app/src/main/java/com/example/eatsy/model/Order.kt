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
    fun getItemString(): String {
        val str = StringBuilder()

        items?.forEach { item ->

            val ite=item as HashMap<String,Any>
            Log.d("Noob", "getItemString: "+ ite["itemQuantity"].toString())
            val menu = (ite["item"] ) as HashMap<String,Any>
            val quantity = ite["itemQuantity"] as Long
            str.append("${menu["name"]}($quantity), ")
        }

        // Remove the trailing comma and space if there are items in the list
        if (str.isNotEmpty()) {
            str.deleteCharAt(str.length - 1) // Remove the trailing comma
            str.deleteCharAt(str.length - 1) // Remove the trailing space
        }

        return str.toString()
    }

}
