package com.example.eatsy.model

class Item (
        val image: String? = null,
        val id:String? = "",
        val restaurantId: String?="",
        val name: String?="",
        val type: String?="",
        val price: Int?=0,
        val isVeg: Boolean?=false
        ): java.io.Serializable
{
        fun getItemName(): String? {
                return name

        }
        fun getItemPrice(): Int? {
                return price
        }
        fun getItemType(): String? {
                return type
        }
}