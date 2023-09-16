package com.example.eatsy.model

import androidx.annotation.DrawableRes
import com.google.firebase.firestore.DocumentReference
import java.io.Serializable
import java.net.URL

data class Restaurants@JvmOverloads constructor(
    val id: String = "",
    val image: String? = null,
    val name: String = "",
    val type: String = "",
    val rating: Double = 0.0,
    val isActive: Boolean = false,
    val isOpen: Boolean = false,
    var menus: ArrayList<String>? = ArrayList(),
    var address: Address?=null,
    ): Serializable {
        companion object{

        }

}