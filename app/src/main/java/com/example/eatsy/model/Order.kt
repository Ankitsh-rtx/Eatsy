package com.example.eatsy.model

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
            if (item is HashMap<*, *>) {
                val itemEntry = item.entries.firstOrNull()
                if (itemEntry != null && itemEntry.key is Item && itemEntry.value is Int) {
                    val menu = itemEntry.key as Item
                    val quantity = itemEntry.value as Int
                    str.append("${menu.name}($quantity), ")
                } else {
                    // Print a message to help debug the data
                    println("Invalid data: $item")
                }
            } else {
                // Print a message to help debug the data
                println("Invalid data type: $item")
            }
        }

        // Remove the trailing comma and space if there are items in the list
        if (str.isNotEmpty()) {
            str.deleteCharAt(str.length - 1) // Remove the trailing comma
            str.deleteCharAt(str.length - 1) // Remove the trailing space
        }

        return str.toString()
    }

}
