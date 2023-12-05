package com.example.gotering_tb_ptb.Data

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val productName: String,
    val productImageUrl: String,
    val productPrice: String,
    val productDisprice: String,
    val productJumlah: String,
    val productTotal: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productName)
        parcel.writeString(productImageUrl)
        parcel.writeString(productPrice)
        parcel.writeString(productDisprice)
        parcel.writeString(productJumlah)
        parcel.writeString(productTotal)
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
