package com.example.eatsy.model

import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone

data class User(
    val id: String?="",
    val name:String?="",
    val age:Int?=null,
    val phone: Phone?=null,
    val email: Email?=null,
    val address: Address?=null,
    val orderId: ArrayList<String>? = null

)
