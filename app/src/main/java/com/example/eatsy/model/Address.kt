package com.example.eatsy.model

import java.io.Serializable

data class Address @JvmOverloads constructor(
    val addressLine:String?="",
    val city:String?="",
    val country:String?="",
    val pincode:Int?=0,
    val street:String?="",
    val state:String?=""
):Serializable {
    override fun toString(): String {
        return "$street, $city, $state, $country, $pincode"
    }


}

