package com.example.eatsy.model
data class Item @JvmOverloads constructor(
        val name: String?="",
        val image: String? = null,
        val id:String? = "",
        val restaurantId: String?="",
        val type: String?="",
        val price: Int?=0,
        @field:JvmField
        val isVeg: Boolean?=null,
        val description:String?=""
        ): java.io.Serializable
{
}

