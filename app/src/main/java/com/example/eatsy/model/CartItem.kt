package com.example.eatsy.model

import android.os.Parcel
import android.os.Parcelable


class CartItem(
    private var item: Item,
    private var quantity: Int,

    ): java.io.Serializable,Parcelable {


    constructor(parcel: Parcel) : this(
        TODO("item"),
        parcel.readInt()
    ) {
    }

    fun getItem(): Item {
        return item
    }
    fun setItem(item:Item) {
        this.item = item
    }


     fun getItemQuantity(): Int {
        return quantity
    }
    fun setItemQuantity(quantity:Int){
        this.quantity = quantity
    }

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other == null || javaClass != other.javaClass) return false
        val cartItem: CartItem = other as CartItem
        return getItemQuantity() == cartItem.getItemQuantity() &&
                getItem().equals(cartItem.getItem())
    }



    override fun toString(): String {
        return "CartItem{" +
                "item=" + item +
                ", quantity=" + quantity +
                '}'
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }


}