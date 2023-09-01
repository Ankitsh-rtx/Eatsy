package com.example.eatsy.model

data class Address(
    private val city:String,
    private val country:String,
    private val pincode:Int,
    private val street:String
):java.io.Serializable {

    fun getCity() :String{
        return city
    }

    fun getCountry():String{
        return country
    }

    fun getPincode(): Int{
        return pincode
    }
    fun getStreet():String{
        return street
    }


}