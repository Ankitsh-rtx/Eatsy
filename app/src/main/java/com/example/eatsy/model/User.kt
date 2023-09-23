package com.example.eatsy.model

import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone

data class User(
    var id: String?="",
    var name:String?="",
    var age:String?=null,
    var phone: String?=null,
    var email: String?=null,
    val address: MutableList<Address> ?= mutableListOf(),
    val orderId: ArrayList<String>? = null,
    val activeOrder:String?=null

)
