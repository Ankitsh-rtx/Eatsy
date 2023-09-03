package com.example.eatsy.model

import java.io.Serializable

data class Address@JvmOverloads constructor(
    val city:String?="",
    val country:String?="",
    val pincode:Int?=0,
    val street:String?=""
):Serializable {


}