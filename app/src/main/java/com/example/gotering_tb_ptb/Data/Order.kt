package com.example.gotering_tb_ptb.Data

import android.os.Parcel
import android.os.Parcelable

data class Order(
    val orderID: String,
    val orderName: String,
    val orderDate: String,
    val ordersArray: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(orderID)
        parcel.writeString(orderName)
        parcel.writeString(orderDate)
        parcel.writeString(ordersArray)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
